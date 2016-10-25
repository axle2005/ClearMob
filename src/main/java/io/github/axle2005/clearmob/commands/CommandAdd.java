package io.github.axle2005.clearmob.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;



import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Config;

public class CommandAdd implements CommandExecutor {
	ClearMob plugin;
	Config config;

	public CommandAdd(ClearMob plugin, Config config) {
		this.plugin = plugin;
		this.config=config;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

}
