package cafe.navy.items.paper.core.component;

import cafe.navy.items.core.ItemComponent;
import org.bukkit.Location;
import org.bukkit.entity.ThrowableProjectile;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.w3c.dom.Entity;

public class ProjectileComponent implements ItemComponent {

    private final @NonNull ThrowableProjectile entity;

    public ProjectileComponent(final @NonNull ThrowableProjectile entity) {
        this.entity = entity;
    }

    public @NonNull Location location() {
        return this.entity.getLocation();
    }

    @Override
    public @NonNull String id() {
        return "projectile";
    }

}
