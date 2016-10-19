package io.github.axle2005.clearmob;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class CommandExec implements CommandExecutor {
	
	ClearMob plugin;
	
	CommandExec(ClearMob plugin) {
		this.plugin = plugin;
		
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		return null;

		
		
	}

}