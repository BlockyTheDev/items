package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ItemThrowContext} is called when an item is thrown, as a projectile.
 */
public class ItemThrowContext implements ItemContext {

    private final @NonNull Item item;

    /**
     * Constructs {@code ItemThrowContext}.
     *
     * @param item the item
     */
    public ItemThrowContext(final @NonNull Item item) {
        this.item = item;
    }

    @Override
    public @NonNull Item item() {
        return this.item;
    }
}
