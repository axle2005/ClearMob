package io.github.axle2005.clearmob.clearers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class ClearEntity {

	public static void run(ClearMob plugin, List<EntityType> list, Collection<World> worlds, CommandSource src) {
		int removedentities = 0;

		Collection<Entity> e = new ArrayList<Entity>();
		for (World world : worlds) {
			for (Entity entity : world.getEntities()) {
				e.add(entity);

			}

		}

		if (!e.isEmpty()) {
			for (Entity entity : e) {
				for (int i = 0; i <= list.size() - 1; i++) {
					if ((entity.getType().equals(list.get(i)))) {
						entity.remove();
						removedentities++;

					}
				}
			}
		}

		feedback(plugin, src, removedentities);

	}

	public static void run(ClearMob plugin, EntityType tile, Collection<World> worlds, CommandSource src) {
		int removedentities = 0;

		Collection<Entity> e = new ArrayList<Entity>();
		for (World world : worlds) {
			for (Entity entity : world.getEntities()) {
				e.add(entity);

			}

		}

		if (!e.isEmpty()) {
			for (Entity entity : e) {
				if (!(entity instanceof Item) && !(entity instanceof Player) && !(entity instanceof ExperienceOrb)) {
					if ((entity.getType().equals(tile))) {
						entity.getLocation().removeBlock(
								Cause.source(Sponge.getPluginManager().fromInstance(plugin).get()).build());
						removedentities++;

					}

				}

			}
		}

		feedback(plugin, src, removedentities);
	}

	private static void feedback(ClearMob plugin, CommandSource src, Integer removed) {
		plugin.getLogger().info(removed + " tile entities were removed");
		if (src instanceof Player) {
			src.sendMessage(Text.of(removed + " tile entities were removed"));
		}
	}

}