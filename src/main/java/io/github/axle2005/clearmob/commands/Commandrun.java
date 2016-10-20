package io.github.axle2005.clearmob.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class Commandrun implements CommandExecutor {

	List<String> listEntities1;
	ClearMob plugin;

	public Commandrun(ClearMob plugin) {
		this.plugin = plugin;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		int removedEntities = 0;
		if (src instanceof Player && !src.hasPermission("clearmob.run")) {
			Player player = (Player) src;

			player.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		} else {

			if (plugin.listtype.equalsIgnoreCase("blacklist")) {
				removedEntities = entityBlackList();
				plugin.getLogger().info(removedEntities + " entities were removed");

				return CommandResult.success();
			} else if (plugin.listtype.equalsIgnoreCase("whitelist")) {
				removedEntities = entityWhiteList();
				plugin.getLogger().info(removedEntities + " entities were removed");

				return CommandResult.success();
			} else {
				plugin.getLogger().error("Problem with Config - ListType");
				return CommandResult.empty();
			}

		}
	}

	public int entityWhiteList() {
		int removedEntities = 0;
		List<String> listEntities = plugin.listEntities;
		for (World world : Sponge.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {
				for (int i = 0; i <= listEntities.size() - 1; i++) {
					if ((entity.getType().getId().equalsIgnoreCase(listEntities.get(i))))

					{
						entity.remove();
						removedEntities++;
					}

				}

			}
		}
		return removedEntities;
	}

	public int entityBlackList() {
		int removedEntities = 0;
		
		for (World world : Sponge.getServer().getWorlds()) {
		    for (Entity entity : world.getEntities()) {
		        if (plugin.listEntities.contains(entity.getType().getId()) || entity.getType().getId().equalsIgnoreCase("minecraft:player")) {
		            		        }
		        else
		        {
		        	entity.remove();
		            removedEntities++;
		        }
		    }
		}
		return removedEntities;
	}

}
