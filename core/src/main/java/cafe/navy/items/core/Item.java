package cafe.navy.items.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class Item {

    private final @NonNull UUID uuid;
    private final @NonNull List<ItemComponent> components;
    private final @NonNull ItemType type;
    private final @NonNull ItemRuntime runtime;

    public Item(final @NonNull UUID uuid,
                final @NonNull List<ItemComponent> components,
                final @NonNull ItemType type,
                final @NonNull ItemRuntime runtime) {
        this.uuid = uuid;
        this.components = components;
        this.type = type;
        this.runtime = runtime;
    }

    public @NonNull UUID uuid() {
        return this.uuid;
    }

    public @NonNull ItemType type() {
        return this.type;
    }

    public @NonNull ItemRuntime runtime() {
        return this.runtime;
    }

    public void removeComponent(final @NonNull Class<? extends ItemComponent> type) {
        this.components.removeIf(component -> component.getClass().equals(type));
    }

    public void addComponent(final @NonNull ItemComponent component) {
        this.components.add(component);
    }

    public <T extends ItemComponent> @NonNull Optional<T> getComponent(final @NonNull Class<T> type) {
        for (final ItemComponent component : this.components) {
            if (component.getClass().equals(type)) {
                return Optional.of((T) component);
            }
        }

        return Optional.empty();
    }

    public <T extends ItemComponent> void useComponent(final @NonNull Class<T> type,
                                                       final @NonNull Consumer<T> consumer) {
        final var componentOpt = this.getComponent(type);
        if (componentOpt.isEmpty()) {
            throw new RuntimeException("No such component");
        }

        consumer.accept(componentOpt.get());
    }

    public <T extends ItemComponent, R> @NonNull R withComponent(final @NonNull Class<T> type,
                                                                 final @NonNull Function<T, R> consumer) {
        final var componentOpt = this.getComponent(type);
        if (componentOpt.isEmpty()) {
            throw new RuntimeException("No such component");
        }

        return consumer.apply(componentOpt.get());
    }

}
