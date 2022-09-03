package cafe.navy.items.core;

import cafe.navy.items.core.context.ItemContext;
import cafe.navy.items.core.context.ItemContextHandler;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ItemType {

    public static @NonNull ItemType GENERIC = ItemType
            .builder("generic")
            .build();

    public static @NonNull Builder builder(final @NonNull String id) {
        return new Builder(id);
    }

    private final @NonNull String id;
    private final @NonNull Map<Class<? extends ItemContext>, List<ItemContextHandler<?>>> handlers;
    private final @NonNull Map<Class<? extends ItemComponent>, Consumer<? extends ItemComponent>> components;

    private ItemType(final @NonNull String id,
                     final @NonNull Map<Class<? extends ItemContext>, List<ItemContextHandler<?>>> handlers,
                     final @NonNull Map<Class<? extends ItemComponent>, Consumer<? extends ItemComponent>> components) {
        this.id = id;
        this.handlers = handlers;
        this.components = components;
    }

    public <T extends ItemContext> void process(final @NonNull T context) {
        if (!this.handlers.containsKey(context.getClass())) {
            return;
        }

        for (final var handler : this.handlers.get(context.getClass())) {
            this.handle(context, handler);
        }
    }

    private <T extends ItemContext> void handle(final @NonNull T context,
                                                final @NonNull ItemContextHandler handler) {
        ((ItemContextHandler<T>) handler).handle(context);
    }

    public @NonNull String id() {
        return this.id;
    }

    public @NonNull Map<Class<? extends ItemComponent>, Consumer<? extends ItemComponent>> components() {
        return this.components;
    }

    public static class Builder {

        private final @NonNull String id;
        private final @NonNull Map<Class<? extends ItemContext>, List<ItemContextHandler<?>>> handlers;
        private final @NonNull Map<Class<? extends ItemComponent>, Consumer<? extends ItemComponent>> components;

        public Builder(final @NonNull String id) {
            this.id = id;
            this.handlers = new HashMap<>();
            this.components = new HashMap<>();
        }

        public <T extends ItemContext> @NonNull Builder on(final @NonNull Class<T> type,
                                                           final @NonNull ItemContextHandler<T> handler) {
            if (!this.handlers.containsKey(type)) {
                this.handlers.put(type, new ArrayList<>());
            }

            this.handlers.get(type).add(handler);

            return this;
        }

        public <T extends ItemComponent> @NonNull Builder with(final @NonNull Class<T> type,
                                                               final @NonNull Consumer<T> configurer) {
            if (this.components.containsKey(type)) {
                throw new RuntimeException("Tried to add multiple components of type " + type.getSimpleName());
            }

            this.components.put(type, configurer);

            return this;
        }

        public @NonNull ItemType build() {
            return new ItemType(this.id, this.handlers, this.components);
        }

    }

}
