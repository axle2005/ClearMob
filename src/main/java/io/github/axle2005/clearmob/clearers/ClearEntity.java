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
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.monster.Boss;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClearEntity {


    private static Map<UUID, Entity> entityData;
    private static int removedEntities = 0;
    private static Task.Builder process = Task.builder();

    public static void run(CommandSource src) {
        ClearMob instance = ClearMob.getInstance();
        removedEntities = 0;

        entityData = new ConcurrentHashMap<>();
        for (World world : Sponge.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                entityData.put(entity.getUniqueId(), entity);
            }
        }


            for (Entity entity : entityData.values()) {

                if (!entity.isRemoved()) {
                    //Skip players and Nametags
                    if (!(entity instanceof Player || entity.get(DisplayNameData.class).isPresent())) {

                        //Removes all Drops
                        if (entity instanceof Item) {
                            if (((itemWhiteListCheck(instance, entity)) == Tristate.FALSE || (instance.getKillItems() && itemWhiteListCheck(instance, entity) != Tristate.TRUE))) {
                                removedEntities++;
                                entity.remove();
                            }
                        } else if (entity instanceof Boss) {
                            if (entityWhiteListCheck(instance, entity) == Tristate.FALSE) {
                                removedEntities++;
                                entity.remove();
                            }
                        }
                        //Kills all Monsters
                        else if ((entityWhiteListCheck(instance, entity) == Tristate.FALSE || (instance.getKillEntity() && entityWhiteListCheck(instance, entity) != Tristate.TRUE))) {
                            removedEntities++;
                            entity.remove();
                        }

                    }


                }

            }






/*
            if (!instance.getTileEntityList().isEmpty()) {
                for (TileEntity te : world.getTileEntities()) {
                    if (instance.getTileEntityList().containsKey(te.getType())) {
                        te.getLocation().removeBlock();
                    }
                }
            }
*/

        Util.feedback("Entity", src, removedEntities);
    }


    public static void run(CommandSource src, EntityType entityType) {
        entityData = new ConcurrentHashMap<>();
        removedEntities = 0;
        for (World world : Sponge.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType().equals(entityType) && !(entity.get(DisplayNameData.class).isPresent())) {
                    removedEntities++;
                    entity.remove();
                }
            }
        }
        Util.feedback("Entities", src, removedEntities);
    }


    private static Tristate entityWhiteListCheck(ClearMob instance, Entity entity) {
        if (instance.getEntityList().containsKey(entity.getType())) {
            if (instance.getEntityList().get(entity.getType()).equals("b")) {
                return Tristate.TRUE;
            } else if (instance.getEntityList().get(entity.getType()).equals("w")) {
                return Tristate.FALSE;
            }

        }
        return Tristate.UNDEFINED;
    }

    private static Tristate itemWhiteListCheck(ClearMob instance, Entity entity) {
        instance.getLogger().info(((Item) entity).getItemType() + "");
        if (instance.getItemList().containsKey(((Item) entity).getItemType())) {
            String value = instance.getItemList().get(((Item) entity).getItemType());
            if (value.equals("b")) {
                return Tristate.TRUE;
            } else if (value.equals("w")) {
                return Tristate.FALSE;
            }

        }
        return Tristate.UNDEFINED;
    }

    private static boolean tileEntityWhiteListCheck(ClearMob instance, Entity entity) {

        if (instance.getTileEntityList().containsKey(((TileEntity) entity).getType())) {
            return instance.getTileEntityList().get(((TileEntity) entity).getType()).equals("w");

        }
        return false;
    }

}