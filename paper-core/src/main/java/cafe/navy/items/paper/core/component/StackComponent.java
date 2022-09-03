package cafe.navy.items.paper.core.component;

import cafe.navy.items.core.ItemComponent;
import cafe.navy.items.paper.core.stack.StackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class StackComponent implements ItemComponent {

    private @NonNull StackBuilder builder;

    public StackComponent() {
        this.builder = StackBuilder.of(Material.COMMAND_BLOCK);
    }

    public void stack(final @NonNull StackBuilder builder) {
        this.builder = builder;
    }

    public void stack(final @NonNull ItemStack stack) {
        this.stack(StackBuilder.of(stack));
    }

    public @NonNull ItemStack getStack() {
        return this.builder.build();
    }

    @Override
    public @NonNull String id() {
        return "stack";
    }

}
