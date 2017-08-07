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

	switch (args) {
	case "entity": {
	    if (arguments.<String>getOne("name").isPresent()) {
		ClearEntity.run(src, Util.getEntityType(arguments.<String>getOne("name").get()));
		return CommandResult.success();
	    }
	    else
	    {
		ClearEntity.run(src);
		return CommandResult.success();
	    }

	}
	case "tileentity": {
	    if (arguments.<String>getOne("name").isPresent()) {
		ClearTileEntity.run(src, Util.getTileEntityType(arguments.<String>getOne("name").get()));
		return CommandResult.success();
	    }
	    else
	    {
		ClearTileEntity.run(src);
		return CommandResult.success();
	    }
	}
	case "items": {

	    // Need to fix.
	    // ClearItems.run(plugin, items, plugin.getWorlds(), src);
	    return CommandResult.success();

	}
	case "xp": {
	    ClearXP.run(plugin, plugin.getWorlds(), src);
	    return CommandResult.success();

	}
	default: {
	    src.sendMessage(Text.of("clearmob <run><tileentity|entity|items|xp>"));
	    return CommandResult.empty();
	}
	}

    }

}
