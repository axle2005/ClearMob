package io.github.axle2005.clearmob.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
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

import io.github.axle2005.clearmob.ClearMob;

public class Commandrun implements CommandExecutor {

	List<String> listEntities1;
	ClearMob plugin;
	
	public Commandrun(ClearMob plugin)  {
		this.plugin = plugin;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		List<String> listEntities = plugin.listEntities;
		if (src instanceof Player) {
			Player player = (Player) src;
			if (!src.hasPermission("clearmob.run")) {

				player.sendMessage(Text.of("You do not have permission to use this command!"));
				// return null;
			} else {
				List<String> list_dump = new ArrayList<String>();
				List<String> list_dump2 = new ArrayList<String>();
				int removedEntities = 0;

				int tempHive = 0;
				for (World world : Sponge.getServer().getWorlds()) {
					for (TileEntity entitytile : world.getTileEntities()) {

						if (!list_dump2.contains(entitytile.toString())) {
							list_dump2.add(entitytile.toString());
						}

						// if(entitytile.getType().getId().equals("torcherino_tile"))
						// {
						// tempTorch++;
						// entitytile.getLocation().removeBlock(Cause.source(plugin).build());
						// }
					}
				}
				for (int i = 0; i <= list_dump2.size() - 1; i++) {
					plugin.getLogger().info(list_dump2.get(i));
				}

				for (World world : Sponge.getServer().getWorlds()) {
					for (Entity entity : world.getEntities()) {
						for (int i = 0; i <= listEntities.size() - 1; i++) {

							if (!list_dump.contains(
									"Name: " + entity.getType().getName() + " Type: " + entity.getType().getId())) {
								list_dump.add(
										"Name: " + entity.getType().getName() + " Type: " + entity.getType().getId());
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
				// plugin.getLogger().info("Number of items on ground:
				// "+tempItems);
				// plugin.getLogger().info("Number of Zombies: "+tempZombies);
				return CommandResult.success();
			}

		} else if (src instanceof ConsoleSource) {

				int removedEntities = 0;
				for(int i=0; i<=listEntities.size()-1;i++)
				{
					plugin.getLogger().info(plugin.listEntities.get(i));
				}
				
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
				int tempHive = 0;
				List<String> list_dump2 = new ArrayList<String>();
				List<TileEntity> listdump = new ArrayList<TileEntity>();

				for (World world : Sponge.getServer().getWorlds()) {
					for (TileEntity entitytile : world.getTileEntities()) {
						listdump.add(entitytile);

					}
				}
				for (int i = 0; i <= listdump.size() - 1; i++) {
					plugin.getLogger().info(listdump.get(i)+"");
				}
				plugin.getLogger().info("Removed Hives: " + tempHive);
				plugin.getLogger().info(removedEntities + " entities were removed");

				return CommandResult.success();
			

		}
		return CommandResult.empty();
	}
}
