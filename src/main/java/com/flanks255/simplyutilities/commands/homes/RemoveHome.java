package com.flanks255.simplyutilities.commands.homes;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.homes.PlayerHomes;
import com.flanks255.simplyutilities.save.HomeDataManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Set;

public class RemoveHome {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("remove-home")
                .requires(cs -> ConfigCache.cmd_home)
                .executes(cs -> setHome(cs, "home"))
                .then(Commands.argument("Name", StringArgumentType.string())
                        .suggests((cs, builder) -> ISuggestionProvider.suggest(getHomesForSuggestion(cs.getSource().asPlayer()),builder))
                        .executes(cs -> setHome(cs, StringArgumentType.getString(cs, "Name"))));
    }

    private static Set<String> getHomesForSuggestion(ServerPlayerEntity playerEntity) {
        HomeDataManager homedata = HomeDataManager.get(playerEntity.getServerWorld());
        return  homedata.getPlayerHomes(playerEntity.getUniqueID(), playerEntity.getDisplayName().getString()).getHomes();
    }

    public static int setHome(CommandContext<CommandSource> ctx, String name) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        HomeDataManager homes = HomeDataManager.get(ctx.getSource().getWorld());

        PlayerHomes playerdata = homes.getPlayerHomes(player.getUniqueID(), player.getDisplayName().getString());

        ctx.getSource().sendFeedback( new TranslationTextComponent(playerdata.removeHome(name)?"message.su.removehomesuccess":"message.su.removehomefail", name), false );
        return 0;
    }
}
