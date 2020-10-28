package com.flanks255.simplyutilities.commands.homes;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.configuration.ServerConfiguration;
import com.flanks255.simplyutilities.homes.PlayerHomes;
import com.flanks255.simplyutilities.save.HomeDataManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class SetHome {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("set-home")
                .requires(cs -> ConfigCache.cmd_home)
                .executes(cs -> setHome(cs, "home"))
                .then(Commands.argument("Name", StringArgumentType.string())
                        .executes(cs -> setHome(cs, StringArgumentType.getString(cs, "Name"))));
    }

    public static int setHome(CommandContext<CommandSource> ctx, String name) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        HomeDataManager homes = HomeDataManager.get(ctx.getSource().getWorld());
        int maxHomes = ServerConfiguration.PLAYER_MAX_HOMES.get();

        PlayerHomes playerdata = homes.getPlayerHomes(player.getUniqueID(), player.getDisplayName().getString());

        if (playerdata.getCount() >= maxHomes && !playerdata.isHome(name) && !ctx.getSource().getEntity().hasPermissionLevel(1)) {
            ctx.getSource().sendErrorMessage(new TranslationTextComponent("message.su.maxhomes", maxHomes));
        }
        else {
            RegistryKey<World> worldKey = player.getServerWorld().getDimensionKey();

            playerdata.setHome(name, worldKey, player.getPosition());
            ctx.getSource().sendFeedback(new TranslationTextComponent("message.su.sethome", name), false);
            homes.setDirty(true);
        }
        return 0;
    }
}
