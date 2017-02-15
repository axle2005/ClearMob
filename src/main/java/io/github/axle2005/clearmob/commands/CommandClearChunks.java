package io.github.axle2005.clearmob.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;

public class CommandClearChunks  implements CommandExecutor{
	ClearMob plugin;

	public CommandClearChunks(ClearMob plugin) {
		this.plugin = plugin;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
		if (!Util.playerPermCheck(src, "clearmob.admin")) {
			return CommandResult.empty();

		} else {
			int unloaded=0;
			for(World w : plugin.getWorlds())
			{
				for(Chunk c : w.getLoadedChunks())
				{
					if(c.unloadChunk())
					{
						unloaded++;
					}
				}
			}
			src.sendMessage(Text.of(TextColors.AQUA,"[ClearMob] Unloaded: "+unloaded+" chunks"));
			return CommandResult.empty();
		}

	}

}