package io.github.axle2005.clearmob.clearers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class ClearTileEntity {

	public static void run(ClearMob plugin, List<TileEntityType> list, Collection<World> worlds, CommandSource src) {
		int removedentities = 0;

		Collection<TileEntity> e = new ArrayList<TileEntity>();
		for (World world : worlds) {
			for (TileEntity entity : world.getTileEntities()) {
				e.add(entity);

			}

		}

		if (!e.isEmpty()) {
			for (TileEntity entity : e) {
				for (int i = 0; i <= list.size() - 1; i++) {
					if ((entity.getType().equals(list.get(i)))) {
						entity.getLocation().removeBlock(
								Cause.source(Sponge.getPluginManager().fromInstance(plugin).get()).build());
						removedentities++;

					}
				}
			}
		}

		feedback(plugin, src, removedentities);

	}

	public static void run(ClearMob plugin, TileEntityType tile, Collection<World> worlds, CommandSource src) {
		int removedentities = 0;

		Collection<TileEntity> e = new ArrayList<TileEntity>();
		for (World world : worlds) {
			for (TileEntity entity : world.getTileEntities()) {
				e.add(entity);

			}

		}

		if (!e.isEmpty()) {
			for (TileEntity entity : e) {
				if ((entity.getType().equals(tile))) {
					entity.getLocation()
							.removeBlock(Cause.source(Sponge.getPluginManager().fromInstance(plugin).get()).build());
					removedentities++;

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
