package cafe.navy.items.paper.core.context;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.context.ItemContext;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemLandContext implements ItemContext {

    private final @NonNull Item item;
    private final @NonNull Location location;

    public ItemLandContext(final @NonNull Item item,
                           final @NonNull Location location) {
        this.item = item;
        this.location = location;
    }

    public @NonNull Location location() {
        return this.location;
    }

    @Override
    public @NonNull Item item() {
        return this.item;
    }
}
