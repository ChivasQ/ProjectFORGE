package com.chivasss.pocket_dimestions.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TestCommand {
    public TestCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("test").then(Commands.literal("test1").executes((context) -> {
            return test1(context.getSource().source);
        })));
    }

    private int test1(CommandSource source) throws CommandSyntaxException {
        source.sendSystemMessage(Component.literal("ikgbk;ljb;"));
        return 1;
    }
}
