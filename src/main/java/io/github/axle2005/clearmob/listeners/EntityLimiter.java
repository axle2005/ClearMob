package io.github.axle2005.clearmob.listeners;

import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.monster.Boss;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.type.Exclude;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class EntityLimiter implements EventListener<SpawnEntityEvent>{

	
	ClearMob plugin;
	
	
	public EntityLimiter(ClearMob plugin)
	{
		this.plugin = plugin;
		
	}
	
	@Override
	@Exclude(SpawnEntityEvent.Spawner.class)
	//@Include(SpawnEntityEvent.Spawner.class)
	public void handle(SpawnEntityEvent event) throws Exception {
		
		List<Entity> entity = event.getEntities();
		Integer count = 0;
		
		for(World w : plugin.worlds)
		{
			for(Entity e:w.getEntities())
			{
				if(e instanceof Monster && !(e instanceof Boss))
				{
					count++;
				}
			}
		}
		for(int i=0; i<entity.size();i++)
		{
			if(entity.get(i) instanceof Monster && (count > plugin.getMobLimit()) && !(entity.get(i) instanceof Boss))
			{
				
				event.setCancelled(true);
			}
		}
		
	}

}
