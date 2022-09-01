package cafe.navy.items.core.loader;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.data.ItemData;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@code ItemLoader} represents an object that can save and load items.
 */
public interface ItemLoader<T extends ItemData<?>> {

    /**
     * Loads an item with the provided {@link UUID} and returns it in a {@link CompletableFuture}.
     *
     * @param uuid the {@link UUID} of the item
     * @return the item,
     */
    @NonNull CompletableFuture<Optional<@NonNull Item<T>>> getItem(final @NonNull UUID uuid);

    /**
     * Saves an item.
     *
     * @param item the item
     * @return a future that completes once the item has finished saving
     */
    @NonNull CompletableFuture<Void> saveItem(final @NonNull Item<T> item);

}
