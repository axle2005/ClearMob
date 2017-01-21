package io.github.axle2005.clearmob.commands;

import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.clearers.clearMain;
import io.github.axle2005.clearmob.clearers.clearTileEntity;

public class CommandRun implements CommandExecutor {

	List<String> listEntities1;
	ClearMob plugin;
	clearMain clearing;

	public CommandRun(ClearMob plugin) {
		this.plugin = plugin;
		clearing = plugin.getClearer();
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
		String args = arguments.<String>getOne("tileentity|entity").get();
	
		if (args.equalsIgnoreCase("entity")) {
			if (src instanceof Player && !src.hasPermission("clearmob.run.entity")) {
				src.sendMessage(Text.of("You do not have permission to use this command!"));
				return CommandResult.empty();
			} else {
				
				clearing.run(plugin.configoptions, plugin.getListType(), plugin.getListEntityType(), src);
				return CommandResult.success();

			}

		} else if (args.equalsIgnoreCase("tileentity")) {

			if (src instanceof Player && !src.hasPermission("clearmob.run.tileentity")) {
				src.sendMessage(Text.of("You do not have permission to use this command!"));
				return CommandResult.empty();
			} else {
				new clearTileEntity(plugin, plugin.getListTileEntityType(), plugin.getWorlds(), src);
				return CommandResult.success();

			}

		} else {
			src.sendMessage(Text.of("clearmob <run><tileentity|entity>"));
			return CommandResult.empty();
		}

	}

}
