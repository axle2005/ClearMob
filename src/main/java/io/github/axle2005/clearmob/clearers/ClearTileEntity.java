/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
