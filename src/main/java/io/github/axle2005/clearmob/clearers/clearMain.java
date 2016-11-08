package io.github.axle2005.clearmob.clearers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class clearMain {

	ClearMob plugin;
	
	int removedEntities = 0;
	int removedTileEntities = 0;
	clearBlackList bl = new clearBlackList();
	clearWhiteList wl = new clearWhiteList();

	public clearMain(ClearMob plugin, Boolean[] configoptions,String ListType, List<String> listEntities, Collection<World> worlds, CommandSource src) {
		this.plugin = plugin;
		

		
		
		
		for (World world : worlds) {
			for (Entity entity : world.getEntities()) {
					removedEntities = removedEntities + run(configoptions,entity,ListType,listEntities);	
			
			}

		}
		

		
		//int removedEntities = totalEntities - e.size();
		feedback(src,removedEntities);
		
	}

	private int run(Boolean[] configoptions, Entity entity, String ListType, List<String> listEntities) {
		int removedEntities = 0;
			if(!entity.isRemoved())
			{
				if (configoptions[1] == true && entity instanceof Monster) {
				
						//KillAllMonsters
					removedEntities++;
						entity.remove();
				}
				else if (configoptions[2] == true && entity.getType().getId().equals("minecraft:item"))
				{
					// KillDrops
					removedEntities++;
					entity.remove();
					
				}
				else if (configoptions[3] == true && entity instanceof Animal) {
					// KillAnimalGroups
					removedEntities++;
					new clearAnimals(entity);
				}
				else if(entity.getType().getId().equals("minecraft:player"))
				{
					
				}
				else
				{
					if (ListType.equalsIgnoreCase("blacklist")) {
						// removedEntities = entityBlackList();
						if(bl.clear(entity,listEntities) ==true)
						{
							removedEntities++;
						}
					} else if (ListType.equalsIgnoreCase("whitelist")) {
						// removedEntities = entityWhiteList();
						if(wl.clear(entity,listEntities) ==true)
						{
							removedEntities++;
						}
					} else {
						plugin.getLogger().error("Problem with Config - ListType");
					}
				}
			
			
		}
		return removedEntities;
		
	}

	private void feedback(CommandSource src, Integer removed) {
		plugin.getLogger().info(removed + " entities were removed");
		if (src instanceof Player) {
			src.sendMessage(Text.of(removed + " entities were removed"));
		}
	}

}
