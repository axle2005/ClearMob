package io.github.axle2005.clearmob.clearers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;

public class ClearMain {

    ClearMob plugin;
    int removedEntities = 0;
    int removedTileEntities = 0;

    Map<UUID, Entity> entityData;
    public ClearMain(ClearMob plugin) {
	this.plugin = plugin;
    }
    
    public void run(CommandSource src) {
	ClearMob instance = ClearMob.getInstance();
	int removedEntities = 0;

	
	entityData = new ConcurrentHashMap<>();
	for (World world : Sponge.getServer().getWorlds()) {
	    for (Entity entity : world.getEntities()) {
		entityData.put(entity.getUniqueId(), entity);
	    } 
	    for (Entity entity : entityData.values()) {
		
		
		if (!entity.isRemoved()) {
		    //Skip players and Nametags
		    if (entity instanceof Player || entity.get(DisplayNameData.class).isPresent()) {

		    } 
		    //Kills all Monsters
		    else if (instance.getGlobalConfig().options.get(0).killAllMonsters == true && entity instanceof Monster) {
			removedEntities++;
			entity.remove();
		    } 
		    //Removes all Drops
		    else if (instance.getGlobalConfig().options.get(0).killAllDrops == true && entity instanceof Item) {
			/*if (ClearItems.run(entity,instance.getGlobalConfig().options.get(0).listItemEntitys,plugin.getitemWB() )) {
			    removedEntities++;
			}*/

		    } 
		    //Kills grouped Animals.
		    else if (instance.getGlobalConfig().options.get(0).killAnimalGroups == true && entity instanceof Animal) {
			removedEntities = removedEntities + ClearAnimals.run(entity);

		    } else {
			if (ClearWhiteList.clear(entity, Util.getEntityType(instance.getGlobalConfig().options.get(0).listEntitys)) == true) {
			    removedEntities++;
			}

		    }

		}

	    }
	}

	ClearTileEntity.run(src);
	feedback(src, removedEntities);

    }

    private void feedback(CommandSource src, Integer removed) {
	plugin.getLogger().info(removed + " entities were removed");
	if (src instanceof Player) {
	    src.sendMessage(Text.of(removed + " entities were removed"));
	}
    }

}
