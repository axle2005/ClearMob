package io.github.axle2005.clearmob.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandSearch implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
		String args = arguments.<String>getOne("tileentity|entity|nearby|all").get();
		if (src instanceof Player && !src.hasPermission("clearmob.dump")) {
			Player player = (Player) src;
			player.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		}

		if (args.equalsIgnoreCase("entity")) {
			// entityDump();
			return CommandResult.success();

		} else {
			src.sendMessage(Text.of("/clearmob <search><entityid>"));
			return CommandResult.empty();
		}
	}

}
