package cafe.navy.items.paper.core;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.ItemComponent;
import cafe.navy.items.core.ItemManager;
import cafe.navy.items.core.ItemType;
import cafe.navy.items.paper.core.component.DamageComponent;
import cafe.navy.items.paper.core.component.StackComponent;
import cafe.navy.items.paper.core.listener.ItemListeners;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PaperItemManager implements ItemManager {

    public static final @NonNull NamespacedKey ITEM_KEY = new NamespacedKey("items", "item");

    public static @NonNull Optional<PaperItemManager> getFromService(final @NonNull JavaPlugin plugin) {
        final var registration = plugin.getServer().getServicesManager().getRegistration(PaperItemManager.class);
        if (registration == null) {
            return Optional.empty();
        }

        return Optional.of(registration.getProvider());
    }

    private final @NonNull JavaPlugin plugin;
    private final @NonNull Map<UUID, Item> items;
    private final @NonNull Map<String, ItemType> types;
    private final @NonNull Map<Class<? extends ItemComponent>, Supplier<? extends ItemComponent>> components;

    public PaperItemManager(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.items = new HashMap<>();
        this.components = new HashMap<>();
        this.types = new HashMap<>();
        this.registerComponent(StackComponent.class, StackComponent::new);
        this.registerComponent(DamageComponent.class, DamageComponent::new);
        this.plugin.getServer().getPluginManager().registerEvents(new ItemListeners(this, this.plugin), this.plugin);

        this.registerType(ItemType.GENERIC);
    }

    public void giveInstance(final @NonNull Player player,
                             final @NonNull Item item) {
        player.getInventory().addItem(this.getInstance(item));
    }

    public @NonNull ItemStack getInstance(final @NonNull Item item) {
        if (item.getComponent(StackComponent.class).isEmpty()) {
            throw new RuntimeException("No stack component!");
        }

        final ItemStack stack = item.withComponent(StackComponent.class, StackComponent::getStack);
        final ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(ITEM_KEY, PersistentDataType.STRING, item.uuid().toString());
        stack.setItemMeta(meta);
        return stack;
    }

    public @NonNull Optional<@NonNull Item> getItem(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.items.get(uuid));
    }

    public @NonNull Optional<@NonNull Item> getItem(final @Nullable ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isEmpty()) {
            return Optional.empty();
        }

        if (!itemStack.hasItemMeta()) {
            return Optional.empty();
        }

        if (!itemStack.getItemMeta().getPersistentDataContainer().has(ITEM_KEY, PersistentDataType.STRING)) {
            return Optional.empty();
        }

        return this.getItem(UUID.fromString(itemStack.getItemMeta().getPersistentDataContainer().get(ITEM_KEY, PersistentDataType.STRING)));
    }

    public <T extends ItemComponent> @NonNull Map<Item, T> getComponents(final @NonNull Class<T> type) {
        final Map<Item, T> items = new HashMap<>();

        for (final Item item : this.items.values()) {
            final var cOpt = item.getComponent(type);
            cOpt.ifPresent(t -> items.put(item, t));
        }

        return items;
    }

    @Override
    public void registerType(final @NonNull ItemType type) {
        this.types.put(type.id(), type);
    }

    @Override
    public @NonNull Item createItem(final @NonNull ItemType type) {
        final List<ItemComponent> components = new ArrayList<>();
        for (final var entry : type.components().entrySet()) {
            final Class<ItemComponent> compType = (Class<ItemComponent>) entry.getKey();
            final Consumer<ItemComponent> compConfigurer = (Consumer<ItemComponent>) entry.getValue();
            final ItemComponent component = this.createComponent(compType);
            this.configure(component, compConfigurer);
            components.add(component);
        }

        final Item item = new Item(
                UUID.randomUUID(),
                components,
                type,
                new PaperItemRuntime(this.plugin)
        );

        this.items.put(item.uuid(), item);

        return item;
    }

    @Override
    public <T extends @NonNull ItemComponent> void registerComponent(@NonNull Class<T> type, @NonNull Supplier<T> generator) {
        this.components.put(type, generator);
    }

    @Override
    public <T extends @NonNull ItemComponent> @NonNull T createComponent(@NonNull Class<T> type) {
        if (!this.components.containsKey(type)) {
            throw new RuntimeException("No supplier");
        }

        return (T) this.components.get(type).get();
    }

    public @NonNull List<ItemType> types() {
        return List.copyOf(this.types.values());
    }

    public @NonNull Optional<ItemType> getType(final @NonNull String id) {
        return Optional.ofNullable(this.types.get(id));
    }

    public <T extends ItemComponent> @NonNull Map<Item, ItemComponent> getItemsWithComponent(final @NonNull Class<? extends ItemComponent> type) {
        final Map<Item, ItemComponent> map = new HashMap<>();
        for (final Item item : this.items.values()) {
            final var opt = item.getComponent(type);
            if (opt.isEmpty()) {
                continue;
            }
            map.put(item, opt.get());
        }
        return map;
    }

    private <T extends @NonNull ItemComponent> void configure(final @NonNull T component,
                                                              final @NonNull Consumer<T> configurer) {
        configurer.accept(component);
    }
}
