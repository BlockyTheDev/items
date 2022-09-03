package cafe.navy.items.core.context;

import cafe.navy.items.core.Item;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface ItemContext {

    @NonNull Item item();

}
