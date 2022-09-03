package cafe.navy.items.paper.plugin;

import cafe.navy.items.paper.core.PaperItemManager;
import cafe.navy.items.paper.plugin.command.CommandService;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemsPlugin extends JavaPlugin {

    private @MonotonicNonNull PaperItemManager manager;
    private @MonotonicNonNull CommandService commandService;

    @Override
    public void onEnable() {
        this.manager = new PaperItemManager(this);
        this.commandService = new CommandService(this.manager, this);
    }

    public @NonNull PaperItemManager itemManager() {
        return this.manager;
    }

}
