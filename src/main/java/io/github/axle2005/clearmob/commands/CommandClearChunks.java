package io.github.axle2005.clearmob.commands;

import io.github.axle2005.clearmob.Util;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.World;

public class CommandClearChunks implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) {
        if (!Util.playerPermCheck(src, "clearmob.admin")) {
            return CommandResult.empty();

        } else {
            int unloaded = 0;
            for (World w : Sponge.getServer().getWorlds()) {
                for (Chunk c : w.getLoadedChunks()) {
                    if (c.unloadChunk()) {
                        unloaded++;
                    }
                }
            }
            src.sendMessage(Text.of(TextColors.AQUA, "[ClearMob] Unloaded: " + unloaded + " chunks"));
            return CommandResult.empty();
        }

    }

}