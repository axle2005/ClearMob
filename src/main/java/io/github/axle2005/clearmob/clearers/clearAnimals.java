package io.github.axle2005.clearmob.clearers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;

public class clearAnimals {
    Map<UUID, Entity> entityData;

    public clearAnimals() {

    }

    public int run(Entity e) {
	int count = 0;
	int removed = 0;
	entityData = new ConcurrentHashMap<>();
	for (Entity en : e.getNearbyEntities(3)) {
	    if (!en.isRemoved()) {
		if (en.getType().equals(e.getType())) {
		    count++;
		    if (count > 10) {
			entityData.put(en.getUniqueId(), en);
		    }
		}

	    }

	}
	for (Entity en : entityData.values()) {
	    if (!en.isRemoved()) {
		if (en.get(DisplayNameData.class).isPresent()) {
		    // Checks if entity has nametag and ignores it.
		} else {
		    en.remove();
		    removed++;
		}
		en.remove();
		removed++;
	    }
	}
	return removed;

    }

}
