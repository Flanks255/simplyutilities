package com.flanks255.simplyutilities.commands.homes;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.homes.HomePoint;
import com.flanks255.simplyutilities.homes.PlayerHomes;
import com.flanks255.simplyutilities.save.HomeDataManager;
import com.flanks255.simplyutilities.utils.MiscUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

import java.util.Set;


public class Home {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("home")
            .requires(cs -> ConfigCache.cmd_home)
            .executes(cs -> home(cs, "home"))
            .then(Commands.argument("Name", StringArgumentType.string())
                .suggests((cs, builder) -> SharedSuggestionProvider.suggest(getHomesForSuggestion(cs.getSource().getPlayerOrException()), builder))
                .executes(cs -> home(cs, StringArgumentType.getString(cs, "Name"))));
    }

    private static Set<String> getHomesForSuggestion(ServerPlayer playerEntity) {
        HomeDataManager homedata = HomeDataManager.get(playerEntity.level());
        return  homedata.getPlayerHomes(playerEntity.getUUID(), playerEntity.getDisplayName().getString()).getHomes();
    }

    public static int home(CommandContext<CommandSourceStack> ctx, String name) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        HomeDataManager homes = HomeDataManager.get(ctx.getSource().getLevel());

        PlayerHomes playerHomes = homes.getPlayerHomes(player.getUUID(), player.getDisplayName().getString());
        if (playerHomes.isHome(name)) {
            HomePoint home = playerHomes.getHome(name);
            ServerLevel targetWorld = player.getServer().getLevel(home.getWorldKey());
            if (targetWorld != null) {
                ctx.getSource().sendSuccess(() -> Component.translatable("message.su.home", name), false);
                //player.teleportTo(targetWorld, home.getPostion().getX(), home.getPostion().getY(), home.getPostion().getZ(), player.getViewYRot(0), player.getViewXRot(0));
                MiscUtils.Teleport(player, targetWorld, home.getPostion(), player.getViewYRot(0), player.getViewXRot(0));
            }
        }
        return 0;
    }
}
