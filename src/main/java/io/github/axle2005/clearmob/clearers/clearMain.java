package io.github.axle2005.clearmob.clearers;

import java.util.Collection;
import java.util.List;

import org.spongepowered.api.Sponge;
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
		int totalEntities = 0;
		
		
		Collection<Entity> e = null;
		for (World world : worlds) {
			for (Entity entity : world.getEntities()) {
				e.add(entity);

			}

		}
		totalEntities = e.size();

		if(!e.isEmpty())
		{
			run(configoptions,e,ListType,listEntities);	
		}
		int removedEntities = totalEntities - e.size();
		feedback(src,removedEntities);
		
	}

	private void run(Boolean[] configoptions, Collection<Entity> e, String ListType, List<String> listEntities) {
		for(Entity entity : e)
		{
			if(!entity.isRemoved())
			{
				if (configoptions[1] == true && entity instanceof Monster) {
				
						//KillAllMonsters
						e.remove(entity);
						entity.remove();
				}
				else if (configoptions[2] == true && entity.getType().getId().equals("minecraft:item"))
				{
					// KillDrops
					e.remove(entity);
					entity.remove();
					
				}
				else if (configoptions[3] == true && entity instanceof Animal) {
					// KillAnimalGroups
					e.remove(entity);
					new clearAnimals(entity);
				}
				else if(entity.getType().getId().equals("minecraft:player"))
				{
					
				}
				else
				{
					if (plugin.listtype.equalsIgnoreCase("blacklist")) {
						// removedEntities = entityBlackList();
						if(bl.clear(entity,listEntities) ==true)
						{
							e.remove(entity);
						}
					} else if (plugin.listtype.equalsIgnoreCase("whitelist")) {
						// removedEntities = entityWhiteList();
						if(bl.clear(entity,listEntities) ==true)
						{
							e.remove(entity);
						}
					} else {
						plugin.getLogger().error("Problem with Config - ListType");
					}
				}
			}
			
		}
		
	}

	private void feedback(CommandSource src, Integer removed) {
		plugin.getLogger().info(removed + " entities were removed");
		if (src instanceof Player) {
			src.sendMessage(Text.of(removed + " entities were removed"));
		}
	}

}
