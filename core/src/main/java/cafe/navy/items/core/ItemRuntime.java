package cafe.navy.items.core;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface ItemRuntime {

    void run(final @NonNull String id,
             final @NonNull Runnable runnable);

    void runDelayed(final @NonNull String id,
                    final int delay,
                    final @NonNull Runnable runnable);

    void runTimed(final @NonNull String id,
                  final int time,
                  final @NonNull Runnable runnable);

    void cancel(final @NonNull String id);

}
