package cafe.navy.items.core.loader;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.data.ItemData;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@code MemoryLoader} saves and loads item using an in-memory map.
 * <p>
 * This loader does not persist data, making it useful for testing.
 *
 * @param <T> the item data type
 */
public final class MemoryLoader<T extends ItemData<?>> implements ItemLoader<T> {

    public static <T extends ItemData<?>> @NonNull MemoryLoader<T> create() {
        return new MemoryLoader<T>();
    }

    private final @NonNull Map<UUID, Item<T>> items;

    private MemoryLoader() {
        this.items = new HashMap<>();
    }

    @Override
    public @NonNull CompletableFuture<Optional<@NonNull Item<T>>> getItem(final @NonNull UUID uuid) {
        return CompletableFuture.completedFuture(Optional.ofNullable(this.items.get(uuid)));
    }

    @Override
    public @NonNull CompletableFuture<Void> saveItem(final @NonNull Item<T> item) {
        this.items.put(item.uuid(), item);
        return CompletableFuture.completedFuture(null);
    }
}
