package io.github.axle2005.clearmob.clearers;

import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

public class clearMobs {

	public List<EntityType> clearMobs(List<EntityType> entities, Entity entity)
	{
		
		@SuppressWarnings("unused")
		int removedEntities = 0;
		
		if (entities.contains(entity.getType())
				|| entity.getType().getId().equalsIgnoreCase("minecraft:player")) {
		} else {
			entity.remove();
			removedEntities++;
		}
		
		return entities;
		
	}
}
