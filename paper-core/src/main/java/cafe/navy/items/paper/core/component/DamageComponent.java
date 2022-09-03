package cafe.navy.items.paper.core.component;

import cafe.navy.items.core.ItemComponent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class DamageComponent implements ItemComponent {

    private double damage;

    public DamageComponent() {
        this(0);
    }

    public DamageComponent(final double damage) {
        this.damage = damage;
    }

    public void damage(final double damage) {
        this.damage = damage;
    }

    public double damage() {
        return this.damage;
    }

    @Override
    public @NonNull String id() {
        return "damage";
    }

}
