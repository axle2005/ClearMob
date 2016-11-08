package io.github.axle2005.clearmob.clearers;

import java.util.List;

import org.spongepowered.api.entity.Entity;

public class clearWhiteList {

	public clearWhiteList()
	{
		
	}
	public Boolean clear(Entity entity, List<String> listEntities) {

		for (int i = 0; i <= listEntities.size() - 1; i++) {
			
				if ((entity.getType().getId().equalsIgnoreCase(listEntities.get(i)))) {
					entity.remove();
					return true;

				}
				
			}
		return false;

		

	}
}
