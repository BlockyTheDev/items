package cafe.navy.items.paper.core.stack;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * {@code StackBuilder} builds {@link ItemStack} instances.
 */
public class StackBuilder {

    public static @NonNull StackBuilder of(final @NonNull Material material) {
        return new StackBuilder(material);
    }

    public static @NonNull StackBuilder of(final @NonNull ItemStack itemStack) {
        final StackBuilder builder = of(itemStack.getType());
        builder.amount = itemStack.getAmount();
        if (itemStack.hasItemMeta()) {
            final ItemMeta meta = itemStack.getItemMeta();
            builder.addingFlags.addAll(meta.getItemFlags());
            if (meta.hasDisplayName()) {
                builder.name = meta.displayName();
            }

            if (meta.hasLore()) {
                builder.lore = Objects.requireNonNullElse(meta.lore(), new ArrayList<>());
            }
        }

        return builder;
    }

    private @NonNull Material material;
    private @Nullable Component name;
    private @NonNull List<Component> lore;
    private @NonNull Set<ItemFlag> addingFlags;
    private int amount;

    private StackBuilder(final @NonNull Material material) {
        this.material = material;
        this.lore = new ArrayList<>();
        this.addingFlags = new HashSet<>();
        this.amount = 1;
    }

    public @NonNull StackBuilder name(final @NonNull Component name) {
        this.name = name;
        return this;
    }

    public @NonNull StackBuilder lore(final @NonNull List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public @NonNull StackBuilder lore(final @NonNull Component... lore) {
        this.lore = new ArrayList<>(List.of(lore));
        return this;
    }

    public @NonNull StackBuilder amount(final int amount) {
        this.amount = amount;
        return this;
    }

    public @NonNull StackBuilder modifyLore(final @NonNull Consumer<List<Component>> modifier) {
        modifier.accept(this.lore);
        return this;
    }

    public @NonNull StackBuilder flags(final @NonNull ItemFlag... flags) {
        this.addingFlags.addAll(Arrays.asList(flags));
        return this;
    }

    public @NonNull StackBuilder hideAllFlags() {
        return this.flags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE
        );
    }

    public @NonNull ItemStack build() {
        final ItemStack stack = new ItemStack(this.material);

        if (this.material.isAir() || this.material.isEmpty()) {
            return stack;
        }

        final ItemMeta meta = stack.getItemMeta();

        if (this.name != null) {
            meta.displayName(this.name);
        }

        meta.lore(this.lore);

        for (final ItemFlag flag : this.addingFlags) {
            meta.addItemFlags(flag);
        }

        stack.setItemMeta(meta);
        stack.setAmount(this.amount);

        return stack;
    }

}
