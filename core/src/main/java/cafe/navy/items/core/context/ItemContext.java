package cafe.navy.items.core.context;

import cafe.navy.items.core.Item;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ItemContext} classes represent an event that is fired when interacting with an {@link Item} in some way.
 */
public interface ItemContext {

    /**
     * Returns the involved item.
     *
     * @return the item
     */
    @NonNull Item item();

}
