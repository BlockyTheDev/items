package cafe.navy.items.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Supplier;

public interface ItemManager {

    void registerType(final @NonNull ItemType type);

    @NonNull Item createItem(final @NonNull ItemType type);

    <T extends @NonNull ItemComponent> void registerComponent(final @NonNull Class<T> type,
                                                              final @NonNull Supplier<T> generator);

    <T extends @NonNull ItemComponent> @NonNull T createComponent(final @NonNull Class<T> type);

}
