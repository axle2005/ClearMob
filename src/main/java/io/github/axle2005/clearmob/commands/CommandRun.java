package io.github.axle2005.clearmob.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.text.Text;
import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;
import io.github.axle2005.clearmob.clearers.ClearEntity;
import io.github.axle2005.clearmob.clearers.ClearItems;
import io.github.axle2005.clearmob.clearers.ClearTileEntity;
import io.github.axle2005.clearmob.clearers.ClearXP;
import io.github.axle2005.clearmob.clearers.clearMain;

public class CommandRun implements CommandExecutor {

	ClearMob plugin;
	clearMain clearing;
	EntityType items;

	public CommandRun(ClearMob plugin) {
		this.plugin = plugin;
		clearing = plugin.getClearer();
		items = Util.getEntityType("minecraft:item");
	}
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
		String args = arguments.<String>getOne("tileentity|entity|items").get();

		if (args.equalsIgnoreCase("entity")) {
			if (!Util.playerPermCheck(src, "clearmob.run.entity")) {
				return CommandResult.empty();
			} else if (arguments.<String>getOne("name").isPresent()) {
				String arg1 = arguments.<String>getOne("name").get();
				ClearEntity.run(plugin, Util.getEntityType(arg1), plugin.getWorlds(), src);
				return CommandResult.success();
			} else {

				clearing.run(plugin.configoptions, plugin.getListEntityType(), src);
				return CommandResult.success();

			}

		} else if (args.equalsIgnoreCase("tileentity")) {

			if (!Util.playerPermCheck(src, "clearmob.run.tileentity")) {
				return CommandResult.empty();
			} else if (arguments.<String>getOne("name").isPresent()) {
				String arg1 = arguments.<String>getOne("name").get();
				ClearTileEntity.run(plugin, Util.getTileEntityType(arg1), plugin.getWorlds(), src);
				return CommandResult.success();
			} else {
				ClearTileEntity.run(plugin, plugin.getListTileEntityType(), plugin.getWorlds(), src);
				return CommandResult.success();

			}

		} else if (args.equalsIgnoreCase("items")) {

			if (!Util.playerPermCheck(src, "clearmob.run.items")) {
				return CommandResult.empty();
			} else {
				ClearItems.run(plugin, items, plugin.getWorlds(), src);
				return CommandResult.success();

			}

		}else if (args.equalsIgnoreCase("xp")) {

			if (!Util.playerPermCheck(src, "xp")) {
				return CommandResult.empty();
			} else {
				ClearXP.run(plugin, plugin.getWorlds(), src);
				return CommandResult.success();

			}

		}
		
		
		else {
			src.sendMessage(Text.of("clearmob <run><tileentity|entity|items>"));
			return CommandResult.empty();
		}

	}

}
