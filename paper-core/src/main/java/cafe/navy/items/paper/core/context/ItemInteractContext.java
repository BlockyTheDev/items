package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemInteractContext implements ItemContext {

    private final @NonNull Item item;
    private final @NonNull Player player;

    /**
     * {@code ItemInteractContext}
     * @param item
     * @param player
     */
    public ItemInteractContext(final @NonNull Item item,
                               final @NonNull Player player) {
        this.item = item;
        this.player = player;
    }
    @Override
    public @NonNull Item item() {
        return null;
    }
}
