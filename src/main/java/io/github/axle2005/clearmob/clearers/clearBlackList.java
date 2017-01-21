package io.github.axle2005.clearmob.clearers;

import java.util.List;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

public class clearBlackList {

	public clearBlackList() {

	}

	public Boolean clear(Entity entity, List<EntityType> list) {

		if (list.contains(entity.getType())) {
			return false;
		} else {
			entity.remove();
			return true;
		}

	}

}
