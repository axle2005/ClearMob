package io.github.axle2005.clearmob.listeners;

import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import io.github.axle2005.clearmob.ClearMob;

public class EntityLimiter implements EventListener<SpawnEntityEvent>{

	ClearMob plugin;
	public EntityLimiter(ClearMob plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	//@Include(SpawnEntityEvent.Spawner.class)
	public void handle(SpawnEntityEvent event) throws Exception {
		Cause cause = event.getCause();
		List<Entity> entity = event.getEntities();
		if(cause.containsNamed("WorldSpawner"))
		{
			event.setCancelled(true);
		}
		if(!event.isCancelled())
		{
			plugin.getLogger().info(cause.toString());
		}
		

		//event.setCancelled(true);
		
	}

}
