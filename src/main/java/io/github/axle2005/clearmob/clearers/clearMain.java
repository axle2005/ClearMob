package io.github.axle2005.clearmob.clearers;

import java.util.List;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class clearMain {

	ClearMob plugin;
	clearAnimals animals = new clearAnimals();
	int removedEntities = 0;
	int removedTileEntities = 0;
	clearBlackList bl = new clearBlackList();
	clearWhiteList wl = new clearWhiteList();

	Scheduler scheduler = Sponge.getScheduler();
	Task.Builder taskBuilder = scheduler.createTaskBuilder();
	Task task = null;

	Task warn = null;

	public clearMain(ClearMob plugin) {
		this.plugin = plugin;
		// int removedEntities = totalEntities - e.size();

	}

	public void run(Boolean[] configoptions, String ListType, List<String> listEntities, CommandSource src) {
		int removedEntities = 0;

		for (World world : Sponge.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {

				if (!entity.isRemoved()) {
					if (entity instanceof Player) {

					} else if (configoptions[1] == true && entity instanceof Monster) {

						// KillAllMonsters
						removedEntities++;
						entity.remove();
					} else if (configoptions[2] == true && entity.getType().getId().equals("minecraft:item")) {
						// KillDrops
						removedEntities++;
						entity.remove();

					} else if (configoptions[3] == true && entity instanceof Animal) {
						// KillAnimalGroups
						animals.run(entity);
						removedEntities++;

					} else {
						if (ListType.equalsIgnoreCase("blacklist")) {
							// removedEntities = entityBlackList();
							if (bl.clear(entity, listEntities) == true) {
								removedEntities++;
							}
						} else if (ListType.equalsIgnoreCase("whitelist")) {
							// removedEntities = entityWhiteList();
							if (wl.clear(entity, listEntities) == true) {
								removedEntities++;
							}
						} else {
							plugin.getLogger().error("Problem with Config - ListType");
						}
						
						// new clearTileEntity(plugin, plugin.listTileEntities,
						// plugin.worlds,
						// Sponge.getServer().getConsole());
						
					}

				}
			}
			

		}
		new clearTileEntity(plugin, plugin.listTileEntities, plugin.worlds, src);
		feedback(src, removedEntities);

	}

	private void feedback(CommandSource src, Integer removed) {
		plugin.getLogger().info(removed + " entities were removed");
		if (src instanceof Player) {
			src.sendMessage(Text.of(removed + " entities were removed"));
		}
	}

}
