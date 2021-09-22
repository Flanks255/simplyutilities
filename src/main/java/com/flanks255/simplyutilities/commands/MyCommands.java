package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.commands.debug.Debug;
import com.flanks255.simplyutilities.commands.homes.Home;
import com.flanks255.simplyutilities.commands.homes.ListHomes;
import com.flanks255.simplyutilities.commands.homes.RemoveHome;
import com.flanks255.simplyutilities.commands.homes.SetHome;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class MyCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("su")
                .then(Spawn.register())
                .then(Bed.register())
                .then(Home.register())
                .then(SetHome.register())
                .then(ListHomes.register())
                .then(RemoveHome.register())
                .then(Debug.register())
                .then(Inhibitor.register())
                .then(Zoom.register())

        );
        dispatcher.register(
                Commands.literal("simplyutilities")
                        .then(Spawn.register())
                        .then(Bed.register())
                        .then(Home.register())
                        .then(SetHome.register())
                        .then(ListHomes.register())
                        .then(RemoveHome.register())
                        .then(Debug.register())
                        .then(Inhibitor.register())
                        .then(Zoom.register())
        );
    }
}
