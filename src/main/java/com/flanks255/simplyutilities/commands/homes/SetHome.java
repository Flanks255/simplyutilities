package com.flanks255.simplyutilities.commands.homes;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.configuration.ServerConfiguration;
import com.flanks255.simplyutilities.homes.PlayerHomes;
import com.flanks255.simplyutilities.save.HomeDataManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class SetHome {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("set-home")
                .requires(cs -> ConfigCache.cmd_home)
                .executes(cs -> setHome(cs, "home"))
                .then(Commands.argument("Name", StringArgumentType.string())
                        .executes(cs -> setHome(cs, StringArgumentType.getString(cs, "Name"))));
    }

    public static int setHome(CommandContext<CommandSourceStack> ctx, String name) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        HomeDataManager homes = HomeDataManager.get(ctx.getSource().getLevel());
        int maxHomes = ServerConfiguration.PLAYER_MAX_HOMES.get();

        PlayerHomes playerdata = homes.getPlayerHomes(player.getUUID(), player.getDisplayName().getString());

        if (playerdata.getCount() >= maxHomes && !playerdata.isHome(name) && !ctx.getSource().getEntity().hasPermissions(1)) {
            ctx.getSource().sendFailure(new TranslatableComponent("message.su.maxhomes", maxHomes));
        }
        else {
            ResourceKey<Level> worldKey = player.getLevel().dimension();

            playerdata.setHome(name, worldKey, player.blockPosition());
            ctx.getSource().sendSuccess(new TranslatableComponent("message.su.sethome", name), false);
            homes.setDirty(true);
        }
        return 0;
    }
}
