package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemDropContext implements ItemContext {

    private final @NonNull Item item;
    private final @NonNull Player player;
    private final org.bukkit.entity.@NonNull Item entity;

    public ItemDropContext(final @NonNull Item item,
                           final @NonNull Player player,
                           final org.bukkit.entity.@NonNull Item entity) {
        this.item = item;
        this.player = player;
        this.entity = entity;
    }

    public @NonNull Player player() {
        return this.player;
    }

    public org.bukkit.entity.@NonNull Item entity() {
        return this.entity;
    }

    @Override
    public @NonNull Item item() {
        return this.item;
    }
}
