package cafe.navy.items.paper.plugin.command;

import cafe.navy.items.core.Item;
import cafe.navy.items.core.ItemType;
import cafe.navy.items.paper.core.PaperItemManager;
import cloud.commandframework.Command;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CommandService {

    private final @NonNull PaperItemManager itemManager;
    private final @NonNull PaperCommandManager<CommandSender> commandManager;
    private final @NonNull StringArgument<CommandSender> typeArg;

    public CommandService(final @NonNull PaperItemManager itemManager,
                          final @NonNull JavaPlugin plugin) {
        this.itemManager = itemManager;
        try {
            this.commandManager = new PaperCommandManager<>(
                    plugin,
                    AsynchronousCommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(),
                    Function.identity()
            );
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        this.typeArg = StringArgument.<CommandSender>newBuilder("type")
                .withSuggestionsProvider((ctx, in) -> {
                    final List<String> out = new ArrayList<>();
                    for (final ItemType type : this.itemManager.types()) {
                        out.add(type.id());
                    }
                    return out;
                })
                .build();


        final Command.Builder<CommandSender> command = this.commandManager.commandBuilder("items", "item", "i");
        this.commandManager.command(command);
        this.registerCommands(command);
    }

    private void registerCommands(final Command.@NonNull Builder<CommandSender> command) {
//        this.commandManager.command(
//                command.literal("list")
//                        .handler(this::handleList)
//        );
//
//        this.commandManager.command(
//                command.literal("give")
//                        .argument(this.typeArg.copy())
//                        .handler(this::handleGive)
//        );

        this.commandManager.command(
                command.literal("modify")
                        .literal("name")
                        .argument(StringArgument.of("name"))
                        .handler(this::handleModifyName)
        );

        this.commandManager.command(
                command.literal("debug")
                        .handler(ctx -> {
                            if (!(ctx.getSender() instanceof Player player)) {
                                return;
                            }

                            player.getInventory().addItem(this.itemManager.getInstance(this.itemManager.createItem(ItemType.GENERIC)));
                        })
        );

        this.commandManager.command(
                command.literal("give")
                        .argument(this.typeArg.copy())
                        .handler(ctx -> {
                            if (!(ctx.getSender() instanceof Player player)) {
                                ctx.getSender().sendMessage(Component.text("You must be a player."));
                                return;
                            }

                            final String id = ctx.get("type");
                            final Optional<ItemType> type = this.itemManager.getType(id);
                            if (type.isEmpty()) {
                                ctx.getSender().sendMessage(Component.text("No type with that ID."));
                                return;
                            }

                            final ItemStack itemStack = this.itemManager.getInstance(this.itemManager.createItem(type.get()));
                            player.getInventory().addItem(itemStack);
                            ctx.getSender().sendMessage(Component.text("You have been given a " + id + ". (" + itemStack.toString() + ")"));
                        })
        );
    }

//    private void handleList(final @NonNull CommandContext<CommandSender> ctx) {
//        final CommandSender sender = ctx.getSender();
//        final List<PaperItemType> types = this.itemManager.getTypes();
//        for (final PaperItemType type : types) {
//            sender.sendMessage(Component.text()
//                    .append(Component.text("- "))
//                    .append(Component.text(type.id())));
//        }
//    }
//
//    private void handleGive(final @NonNull CommandContext<CommandSender> ctx) {
//        final CommandSender sender = ctx.getSender();
//        if (!(sender instanceof Player player)) {
//            return;
//        }
//
//        final String typeId = ctx.get("type");
//        final var typeOpt = this.itemManager.getType(typeId);
//        if (typeOpt.isEmpty()) {
//            sender.sendMessage(Component.text("There is no type with that ID.", NamedTextColor.RED));
//            return;
//        }
//        final var type = typeOpt.get();
//        final PaperItem item = type.createItem(this.itemManager);
//        this.itemManager.giveInstance(player, item);
//    }

    private void handleModifyName(final @NonNull CommandContext<CommandSender> ctx) {
        if (!(ctx.getSender() instanceof Player player)) {
            return;
        }

        final ItemStack stack = player.getEquipment().getItemInMainHand();
        if (stack == null || stack.getType().isEmpty()) {
            return;
        }

        final Component name = MiniMessage.miniMessage().deserialize(ctx.get("name"));

        final Optional<Item> itemOpt = this.itemManager.getItem(stack);
        if (itemOpt.isEmpty()) {
            final ItemMeta meta = stack.getItemMeta();
            meta.displayName(name);
            stack.setItemMeta(meta);
            player.getEquipment().setItemInMainHand(stack);
        } else {
//            final PaperItem item = itemOpt.get();
//            item.data().name(name);
        }
    }


}
