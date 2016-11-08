package io.github.axle2005.clearmob.clearers;

import java.util.Collection;

import org.spongepowered.api.entity.Entity;

import io.github.axle2005.clearmob.ClearMob;

public class clearAnimals {

	ClearMob plugin;
	public clearAnimals(ClearMob plugin)
	{
		this.plugin = plugin; 
	}
	
	private int clearGroups(Entity e) {
		String name = e.getType().getId();
		int count = 0;
		Collection<Entity> entities = e.getNearbyEntities(20);
		if(!entities.isEmpty())
		{
			
		
		for (Entity en : entities) {
			if (!en.isRemoved()) {
				if (en.getType().getId().equals(name)) {
					count++;
					if (count > 5) {
						en.remove();
					}
				}

			}
		}
		}
		if (count > 5) {
			return count - 5;
		} else
			return 0;

	}
}
