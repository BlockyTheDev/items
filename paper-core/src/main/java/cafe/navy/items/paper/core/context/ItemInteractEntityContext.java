package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ItemInteractEntityContext} is called when an item is used on an entity.
 */
public class ItemInteractEntityContext implements ItemContext {

    private final @NonNull Item item;
    private final @NonNull Player player;

    /**
     * Constructs {@code ItemInteractEntityContext}.
     *
     * @param item the item
     */
    public ItemInteractEntityContext(final @NonNull Item item,
                                     final @NonNull Player player) {
        this.item = item;
        this.player = player;
    }

    /**
     * Returns the player who used the item.
     *
     * @return a player
     */
    public @NonNull Player player() {
        return this.player;
    }

    @Override
    public @NonNull Item item() {
        return null;
    }
}
