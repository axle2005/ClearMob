package io.github.axle2005.clearmob.commands;

import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import io.github.axle2005.clearmob.ClearMob;

public class CommandInfo implements CommandExecutor {

	ClearMob plugin;

	public CommandInfo(ClearMob plugin) {
		this.plugin = plugin;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {

		String type = arguments.<String>getOne("range/hand").get();

		if (src instanceof Player && !src.hasPermission("clearmob.info")) {
			Player player = (Player) src;
			player.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		} else if (src instanceof Player) {
			Player player = (Player) src;

			if (type.equalsIgnoreCase("hand")) {
				player.sendMessage(Text.of(player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getId()));

				return CommandResult.success();
			} else if (type.equalsIgnoreCase("range")) {
				Integer range = arguments.<Integer>getOne("range").get();
				Collection<Entity> e = player.getNearbyEntities(range);

				for (Entity en : e) {
					plugin.getLogger().info("Entity: " + en.getType().getId());
				}
				return CommandResult.success();
			} else {
				src.sendMessage(Text.of("clearmob <info><range|hand>"));
				return CommandResult.empty();
			}

		} else {
			src.sendMessage(Text.of("You can not run this command"));
			return CommandResult.empty();
		}

	}

}