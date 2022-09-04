package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ItemPickUpContext} is called when an item is picked up.
 */
public class ItemPickUpContext implements ItemContext {

    private final @NonNull Item item;
    private final @NonNull Player player;

    /**
     * Constructs {@code ItemPickUpContext}.
     *
     * @param item   the item
     * @param player the player
     */
    public ItemPickUpContext(final @NonNull Item item,
                             final @NonNull Player player) {
        this.item = item;
        this.player = player;
    }

    /**
     * Returns the player who picked up the item.
     *
     * @return a player
     */
    public @NonNull Player player() {
        return this.player;
    }

    @Override
    public @NonNull Item item() {
        return this.item;
    }
}
