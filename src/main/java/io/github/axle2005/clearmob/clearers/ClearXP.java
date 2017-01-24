package io.github.axle2005.clearmob.clearers;

import java.util.ArrayList;
import java.util.Collection;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

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

		feedback(plugin, src, removedentities);
		return removedentities;

	}


	private static void feedback(ClearMob plugin, CommandSource src, Integer removed) {
		plugin.getLogger().info(removed + " entities were removed");
		if (src instanceof Player) {
			src.sendMessage(Text.of(removed + " entities were removed"));
		}
	}

}
