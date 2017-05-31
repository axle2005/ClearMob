package io.github.axle2005.clearmob.clearers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Item;
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
    }

    public void run(Boolean[] configoptions, List<EntityType> listEntityType, CommandSource src) {
	int removedEntities = 0;

	Map<UUID, Entity> entityData = new ConcurrentHashMap<>();
	for (World world : Sponge.getServer().getWorlds()) {
	    for (Entity entity : world.getEntities()) {

		entityData.put(entity.getUniqueId(), entity);
	    } 
	    for (Entity entity : entityData.values()) {
		if (!entity.isRemoved()) {
		    if (entity instanceof Player) {

		    } else if (entity.get(DisplayNameData.class).isPresent()) {
			// Checks if entity has nametag and ignores it.
		    } else if (configoptions[1] == true && entity instanceof Monster) {

			// KillAllMonsters
			removedEntities++;
			entity.remove();
		    } else if (configoptions[2] == true && entity instanceof Item) {
			// KillDrops
			if (ClearItems.run(entity,plugin.getListItemType(),plugin.getitemWB() )) {
			    removedEntities++;
			}

		    } else if (configoptions[3] == true && entity instanceof Animal) {
			// KillAnimalGroups
			removedEntities = removedEntities + animals.run(entity);

		    } else {
			if (wl.clear(entity, plugin.getListEntityType()) == true) {
			    removedEntities++;
			}

		    }

		}

	    }
	}

	ClearTileEntity.run(plugin, plugin.getListTileEntityType(), plugin.getWorlds(), src);
	feedback(src, removedEntities);

    }

    public void run(EntityType entity) {

    }

    private void feedback(CommandSource src, Integer removed) {
	plugin.getLogger().info(removed + " entities were removed");
	if (src instanceof Player) {
	    src.sendMessage(Text.of(removed + " entities were removed"));
	}
    }

}
