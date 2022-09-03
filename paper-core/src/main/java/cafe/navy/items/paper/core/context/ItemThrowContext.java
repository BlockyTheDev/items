package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.bukkit.entity.ThrowableProjectile;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemThrowContext implements ItemContext {

    private final @NonNull Item item;

    public ItemThrowContext(final @NonNull Item item) {
        this.item = item;
    }

    @Override
    public @NonNull Item item() {
        return this.item;
    }
}
