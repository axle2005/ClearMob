package io.github.axle2005.clearmob.clearers;

import java.util.Map;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;

public class ClearTileEntity {

    private static Map<Integer, TileEntity> entityData;
    private static int removedEntities = 0;
    private static int index = 0;

    public static void run(CommandSource src) {
	ClearMob instance = ClearMob.getInstance();
	index = 0;

	for (World world : Sponge.getServer().getWorlds()) {
	    for (TileEntity entity : world.getTileEntities()) {
		entityData.put(index, entity);
		index++;

	    }

	}
	for (TileEntity entity : entityData.values()) {
	    if (instance.getGlobalConfig().options.get(0).listTileEntitys.contains(entity.getType())) {
		entity.getLocation()
			.removeBlock(Cause.source(Sponge.getPluginManager().fromInstance(instance)).build());
		removedEntities++;
	    }
	}
	Util.feedback("Tile Entity", src, removedEntities);

    }

    public static void run(CommandSource src, TileEntityType type) {
	ClearMob instance = ClearMob.getInstance();
	index = 0;

	for (World world : Sponge.getServer().getWorlds()) {
	    for (TileEntity entity : world.getTileEntities()) {
		if (entity.getType().equals(type)) {
			entity.getLocation()
				.removeBlock(Cause.source(Sponge.getPluginManager().fromInstance(instance)).build());
			removedEntities++;
		    }

	    }

	}
	Util.feedback("Tile Entity", src, removedEntities);

    }

}
