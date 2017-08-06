package io.github.axle2005.clearmob.clearers;

import java.util.ArrayList;
import java.util.Collection;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;

public class ClearXP {

	public static int run(ClearMob plugin, Collection<World> worlds, CommandSource src) {
		int removedentities = 0;

		Collection<Entity> e = new ArrayList<Entity>();
		for (World world : worlds) {
			for (Entity entity : world.getEntities()) {
				e.add(entity);

			}

		}

		if (!e.isEmpty()) {
			for (Entity entity : e) {
					if ((entity instanceof ExperienceOrb)) {
						entity.remove();
						removedentities++;

					
				}
			}
		}

		Util.feedback("Experience Orbs", src, removedentities);
		return removedentities;

	}




}
