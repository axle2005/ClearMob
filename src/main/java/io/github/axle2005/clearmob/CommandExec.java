package io.github.axle2005.clearmob;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class CommandExec implements CommandExecutor {
 
	ClearMob plugin;
	List<String> listEntities1; 


	CommandExec(ClearMob plugin) {
		this.plugin = plugin;

	}

	CommandExec(ClearMob plugin, List<String> listEntities) {
		this.plugin = plugin;
		this.listEntities1 = listEntities;
		
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		List<String> listEntities = plugin.listEntities;
		String cmd_args = args.<String> getOne("run").get();
		if (src instanceof Player) {
			Player player = (Player) src;
			if (!src.hasPermission("clearmob.run")) {

				player.sendMessage(Text.of("You do not have permission to use this command!"));
				//return null;
			} else {
				if (cmd_args.equalsIgnoreCase("run")) {

					List<String> list_dump = new ArrayList<String>();
					int removedEntities = 0;
					int tempItems = 0;
					int tempZombies =0;
					for (World world : Sponge.getServer().getWorlds()) {
						for (Entity entity : world.getEntities()) {
							// con.sendMessage(Text.of(entity.getType().getName()));
							for (int i = 0; i <= listEntities.size() - 1; i++) {
								if((entity.getType().getId().equals("minecraft:item")))
								{
									tempItems++;
								}
								if((entity.getType().getId().equals("minecraft:zombie")))
								{
									tempZombies++;
								}
								if (!list_dump.contains(
										"Name: " + entity.getType().getName() + " Type: " + entity.getType().getId())) {
									list_dump.add("Name: " + entity.getType().getName() + " Type: "
											+ entity.getType().getId());
								}

								if ((entity.getType().getId().equalsIgnoreCase(listEntities.get(i))))
								// + " instanceof Monster))
								{

									entity.remove();
									// plugin.getLogger().info(listEntities.get(i));
									removedEntities++;
								}

							}

						}
					}
					plugin.getLogger().info(removedEntities + " entities were removed");
					src.sendMessage(Text.of("[ClearMob] " + removedEntities + " entities were removed"));
					for (int i = 0; i <= list_dump.size() - 1; i++) {
						plugin.getLogger().info(list_dump.get(i));
					}
					plugin.getLogger().info("Number of items on ground: "+tempItems);
					plugin.getLogger().info("Number of Zombies: "+tempZombies);
					return CommandResult.success();
				}
			}

		} else if (src instanceof ConsoleSource) {
			if (cmd_args.equalsIgnoreCase("run")) {

				int removedEntities = 0;
				for (World world : Sponge.getServer().getWorlds()) {
					for (Entity entity : world.getEntities()) {
						for (int i = 0; i <= listEntities.size() - 1; i++) {

							if ((entity.getType().getId().equalsIgnoreCase(listEntities.get(i))))
							// + " instanceof Monster))
							{

								entity.remove();
								// plugin.getLogger().info(listEntities.get(i));
								removedEntities++;
							}
						}

					}
				}
				plugin.getLogger().info(removedEntities + " entities were removed");

				return CommandResult.success();
			}

		}
		return CommandResult.empty();
	}

}