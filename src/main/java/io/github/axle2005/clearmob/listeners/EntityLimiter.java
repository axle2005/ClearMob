package io.github.axle2005.clearmob.listeners;

import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.living.monster.Boss;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class EntityLimiter{

	
	ClearMob plugin;
	
	
	public EntityLimiter(ClearMob plugin)
	{
		this.plugin = plugin;
		
	}
	
	@Listener(beforeModifications=true)
	public void handle(SpawnEntityEvent event) throws Exception  {
		
		List<Entity> entity = event.getEntities();
		Integer count = 0;
		Integer xpcount=0;
		
		for(World w : plugin.getWorlds())
		{
			for(Entity e:w.getEntities())
			{
				if(e instanceof Monster && !(e instanceof Boss))
				{
					count++;
				}
				if(e instanceof ExperienceOrb)
				{
					xpcount++;
				}
			}
		}
		for(int i=0; i<entity.size();i++)
		{
			if(entity.get(i) instanceof Monster && (count > plugin.getMobLimit()) && !(entity.get(i) instanceof Boss))
			{
				
				event.setCancelled(true);
			}
			if((entity.get(i) instanceof ExperienceOrb && entity.get(i).getNearbyEntities(10).size()>20))
			{
				event.setCancelled(true);
			}
		}
	
		
	}

}
