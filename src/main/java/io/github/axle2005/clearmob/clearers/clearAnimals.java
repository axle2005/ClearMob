package io.github.axle2005.clearmob.clearers;

import java.util.Collection;

import org.spongepowered.api.entity.Entity;

public class clearAnimals {

	public clearAnimals(Entity e) {
		String name = e.getType().getId();
		int count = 0;
		Collection<Entity> entities = e.getNearbyEntities(20);
		if (!entities.isEmpty() && entities != null) {

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

	}

}
