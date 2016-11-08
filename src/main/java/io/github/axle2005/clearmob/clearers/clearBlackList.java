package io.github.axle2005.clearmob.clearers;

import java.util.Collection;
import java.util.List;

import org.spongepowered.api.entity.Entity;

public class clearBlackList {

	public clearBlackList() {
		
	}

	public Boolean clear(Entity entity, List<String> listEntities) {

		if (listEntities.contains(entity.getType().getId())) {
			return false;
		} else {
			entity.remove();
			return true;
		}

	}

}
