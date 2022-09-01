package cafe.navy.items.core.data;

import cafe.navy.items.core.exception.ItemInstanceCreateException;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code ItemData} represents an object that stores an item's data.
 * <p>
 * Depending on the platform implementation, the {@link ItemData#createInstance()} method can be used to obtain
 * an object usable with that platform's 'inventory' type.
 *
 * @param <T> the item type
 */
public interface ItemData<T> {

    /**
     * Creates an instance of {@code T}.
     *
     * @return the item instance
     * @throws ItemInstanceCreateException if an error occurred while
     *                                     creating the item
     */
    @NonNull T createInstance() throws ItemInstanceCreateException;

}
