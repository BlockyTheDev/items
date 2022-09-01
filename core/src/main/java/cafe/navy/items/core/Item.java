package cafe.navy.items.core;

import cafe.navy.items.core.data.ItemData;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class Item<T extends ItemData<?>> {

    private final @NonNull UUID uuid;
    private final @NonNull T data;

    public Item(final @NonNull UUID uuid,
                final @NonNull T data) {
        this.uuid = uuid;
        this.data = data;
    }

    public @NonNull UUID uuid() {
        return this.uuid;
    }

}
