package io.github.axle2005.clearmob.clearers;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClearTileEntity {

    private static Map<Integer, TileEntity> entityData;
    private static int removedEntities = 0;
    private static int index = 0;

    public static void run(CommandSource src) {
        ClearMob instance = ClearMob.getInstance();
        entityData = new ConcurrentHashMap<>();
        index = 0;

        for (World world : Sponge.getServer().getWorlds()) {
            for (TileEntity entity : world.getTileEntities()) {
                entityData.put(index, entity);
                index++;

            }

        }
        for (TileEntity entity : entityData.values()) {
            if (Util.getTileEntityType(instance.getGlobalConfig().options.get(0).listTileEntitys).contains(entity.getType())) {
                entity.getLocation()
                        .removeBlock();
                removedEntities++;
            }
        }
        Util.feedback("Tile Entity", src, removedEntities);

    }

    public static void run(CommandSource src, TileEntityType type) {
        entityData = new ConcurrentHashMap<>();
        index = 0;

        for (World world : Sponge.getServer().getWorlds()) {
            for (TileEntity entity : world.getTileEntities()) {
                if (entity.getType().equals(type)) {
                    entity.getLocation()
                            .removeBlock();
                    removedEntities++;
                }

            }

        }
        Util.feedback("Tile Entity", src, removedEntities);

    }

}
