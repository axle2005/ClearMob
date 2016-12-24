package io.github.axle2005.clearmob.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;



public class CommandInfo implements CommandExecutor {


	public CommandInfo() {
		
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player && !src.hasPermission("clearmob.info")) {
			Player player = (Player) src;
			player.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		}
		else if(src instanceof Player)
		{
			Player player = (Player) src;
			player.sendMessage(Text.of(player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getId()));
			return CommandResult.success();
		}
		else
		{
			src.sendMessage(Text.of("You can not run this command"));
			return CommandResult.empty();
		}
		
	}

}