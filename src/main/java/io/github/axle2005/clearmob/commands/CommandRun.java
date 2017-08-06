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
import io.github.axle2005.clearmob.clearers.ClearTileEntity;
import io.github.axle2005.clearmob.clearers.ClearXP;
import io.github.axle2005.clearmob.clearers.ClearMain;

public class CommandRun implements CommandExecutor {

    ClearMob plugin;
    ClearMain clearing;
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
	    if (!Util.playerPermCheck(src, "clearmob.admin")) {
		return CommandResult.empty();
	    } else {
		String arg1 = "abc";
		try {
		    if (arguments.<String>getOne("name").isPresent()) {
			arg1 = arguments.<String>getOne("name").get();
		    }
		} catch (IllegalArgumentException e) {

		}
		if (!arg1.equalsIgnoreCase("abc")) {

		    ClearEntity.run(plugin, Util.getEntityType(arg1), plugin.getWorlds(), src);
		    return CommandResult.success();
		} else {

		    clearing.run(src);
		    return CommandResult.success();

		}
	    }

	} else if (args.equalsIgnoreCase("tileentity")) {

	    ClearMob instance = ClearMob.getInstance();
	    if (!Util.playerPermCheck(src, "clearmob.admin")) {
		return CommandResult.empty();
	    } else {
		String arg1 = "abc";
		try {
		    if (arguments.<String>getOne("name").isPresent()) {
			arg1 = arguments.<String>getOne("name").get();
		    }
		} catch (IllegalArgumentException e) {

		}
		if (!arg1.equalsIgnoreCase("abc")) {

		    ClearTileEntity.run(plugin, Util.getTileEntityType(arg1), plugin.getWorlds(), src);
		    return CommandResult.success();
		} else {

		    ClearTileEntity.run(plugin, instance.getGlobalConfig().options.get(0).listTileEntitys, plugin.getWorlds(), src);
		    return CommandResult.success();

		}
	    }

	} else if (args.equalsIgnoreCase("items")) {

	    if (!Util.playerPermCheck(src, "clearmob.admin")) {
		return CommandResult.empty();
	    } else {

		// Need to fix.
		// ClearItems.run(plugin, items, plugin.getWorlds(), src);
		return CommandResult.success();

	    }

	} else if (args.equalsIgnoreCase("xp")) {

	    if (!Util.playerPermCheck(src, "xp")) {
		return CommandResult.empty();
	    } else {
		ClearXP.run(plugin, plugin.getWorlds(), src);
		return CommandResult.success();

	    }

	}

	else {
	    src.sendMessage(Text.of("clearmob <run><tileentity|entity|items|xp>"));
	    return CommandResult.empty();
	}

    }

}
