package cafe.navy.items.paper.core;

import cafe.navy.items.core.ItemRuntime;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class PaperItemRuntime implements ItemRuntime {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull Map<String, BukkitTask> tasks;

    public PaperItemRuntime(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.tasks = new HashMap<>();
    }

    @Override
    public void run(@NonNull String id, @NonNull Runnable runnable) {
        this.tasks.put(id, new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
                tasks.remove(id);
            }
        }.runTask(this.plugin));
    }

    @Override
    public void runDelayed(@NonNull String id, int delay, @NonNull Runnable runnable) {
        this.tasks.put(id, new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
                tasks.remove(id);
            }
        }.runTaskLater(this.plugin, delay));
    }

    @Override
    public void runTimed(@NonNull String id, int time, @NonNull Runnable runnable) {
        this.tasks.put(id, new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(this.plugin, time, time));
    }

    @Override
    public void cancel(@NonNull String id) {
        if (this.tasks.containsKey(id)) {
            this.tasks.get(id).cancel();
            this.tasks.remove(id);
        }
    }
}
