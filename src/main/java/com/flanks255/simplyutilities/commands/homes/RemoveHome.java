package com.flanks255.simplyutilities.commands.homes;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.homes.PlayerHomes;
import com.flanks255.simplyutilities.save.HomeDataManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Set;

public class RemoveHome {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("remove-home")
            .requires(cs -> ConfigCache.cmd_home)
            .executes(cs -> setHome(cs, "home"))
            .then(Commands.argument("Name", StringArgumentType.string())
                .suggests((cs, builder) -> SharedSuggestionProvider.suggest(getHomesForSuggestion(cs.getSource().getPlayerOrException()),builder))
                .executes(cs -> setHome(cs, StringArgumentType.getString(cs, "Name"))));
    }

    private static Set<String> getHomesForSuggestion(ServerPlayer playerEntity) {
        HomeDataManager homedata = HomeDataManager.get(playerEntity.level);
        return  homedata.getPlayerHomes(playerEntity.getUUID(), playerEntity.getDisplayName().getString()).getHomes();
    }

    public static int setHome(CommandContext<CommandSourceStack> ctx, String name) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        HomeDataManager homes = HomeDataManager.get(ctx.getSource().getLevel());

        PlayerHomes playerdata = homes.getPlayerHomes(player.getUUID(), player.getDisplayName().getString());

        ctx.getSource().sendSuccess( new TranslatableComponent(playerdata.removeHome(name)?"message.su.removehomesuccess":"message.su.removehomefail", name), false );
        return 0;
    }
}
