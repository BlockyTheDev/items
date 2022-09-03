package cafe.navy.items.paper.core.listener;

import cafe.navy.items.core.Item;
import cafe.navy.items.paper.core.PaperItemManager;
import cafe.navy.items.paper.core.component.DamageComponent;
import cafe.navy.items.paper.core.component.ProjectileComponent;
import cafe.navy.items.paper.core.context.ItemLandContext;
import cafe.navy.items.paper.core.context.ItemThrowContext;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

public final class ItemListeners implements Listener {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull PaperItemManager manager;

    public ItemListeners(final @NonNull PaperItemManager manager,
                         final @NonNull JavaPlugin plugin) {
        this.manager = manager;
        this.plugin = plugin;
    }

    @EventHandler
    private void onProjectileThrow(final @NonNull ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof ThrowableProjectile throwable) {
            final ItemStack stack = throwable.getItem();
            this.manager.getItem(stack)
                    .ifPresent(item -> {
                        item.addComponent(new ProjectileComponent(throwable));
                        item.type().process(new ItemThrowContext(item));
                    });
        }

    }

    @EventHandler
    private void onProjectileLand(final @NonNull ProjectileHitEvent event) {
        System.out.println("ProjectileHitEvent");
        if (event.getEntity() instanceof ThrowableProjectile throwable) {
            final Location location = event.getEntity().getLocation();
            final ItemStack stack = throwable.getItem();
            this.manager.getItem(stack)
                    .ifPresent(item -> {
                        item.removeComponent(ProjectileComponent.class);
                        item.type().process(new ItemLandContext(item, location));
                    });
        }
    }

    @EventHandler
    private void handleDamage(final @NonNull EntityDamageByEntityEvent event) {
        final Entity damager = event.getDamager();
        if (!(damager instanceof LivingEntity living)) {
            return;
        }

        final ItemStack itemStack = living.getEquipment().getItemInMainHand();
        final Optional<Item> itemOpt = this.manager.getItem(itemStack);
        if (itemOpt.isEmpty()) {
            return;
        }

        final Item item = itemOpt.get();
        final Optional<DamageComponent> opt = item.getComponent(DamageComponent.class);
        if (opt.isEmpty()) {
            return;
        }

        event.setDamage(opt.get().damage());
    }

}
