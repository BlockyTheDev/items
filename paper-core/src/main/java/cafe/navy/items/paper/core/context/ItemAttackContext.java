package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.bukkit.entity.LivingEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ItemAttackContext}
 */
public class ItemAttackContext implements ItemContext {

    private final @NonNull Item item;
    private final @NonNull LivingEntity attacker;
    private final @NonNull LivingEntity attacked;

    public ItemAttackContext(final @NonNull Item item,
                             final @NonNull LivingEntity attacker,
                             final @NonNull LivingEntity attacked) {
        this.item = item;
        this.attacked = attacked;
        this.attacker = attacker;
    }

    public @NonNull LivingEntity attacker() {
        return this.attacker;
    }

    public @NonNull LivingEntity attacked() {
        return this.attacked;
    }

    @Override
    public @NonNull Item item() {
        return this.item;
    }
}
