package io.github.axle2005.clearmob.clearers;

import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.monster.Monster;

public class clearMobs {

	public List<String> clearMobs(List<String> entities, Entity entity)
	{
		
		int removedEntities = 0;

		if (entities.contains(entity.getType().getId())
				|| entity.getType().getId().equalsIgnoreCase("minecraft:player")) {
		} else {
			entity.remove();
			removedEntities++;
		}
		
		return entities;
		
	}
}
