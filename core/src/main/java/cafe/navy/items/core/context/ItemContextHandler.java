package cafe.navy.items.core.context;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface ItemContextHandler<T> {

    void handle(final @NonNull T ctx);

}
