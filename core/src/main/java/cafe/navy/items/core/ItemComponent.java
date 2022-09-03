package cafe.navy.items.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

public interface ItemComponent {

    @NonNull String id();

    default @NonNull String description() {
        return this.id();
    };

}
