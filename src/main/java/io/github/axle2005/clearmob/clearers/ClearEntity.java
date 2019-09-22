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
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Boss;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClearEntity {


    private static Map<UUID, Entity> entityData;
    private static int removedEntities = 0;

    public static void run(CommandSource src) {
        ClearMob instance = ClearMob.getInstance();
        removedEntities = 0;

        entityData = new ConcurrentHashMap<>();
        for (World world : Sponge.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                entityData.put(entity.getUniqueId(), entity);
            }
            for (Entity entity : entityData.values()) {
                if (!entity.isRemoved()) {
                    //Skip players and Nametags
                    if (!(entity instanceof Player || entity.get(DisplayNameData.class).isPresent())) {
                        //Kills all Monsters
                        if (instance.getGlobalConfig().options.get(0).killAllMonsters && entity instanceof Monster && !(entity instanceof Boss)) {
                            removedEntities++;
                            entity.remove();
                        }
                        //Kills all Bosses
                        if (instance.getGlobalConfig().options.get(0).killAllBosses && entity instanceof Boss) {
                            removedEntities++;
                            entity.remove();
                        }

                        //Removes all Drops
                        if (instance.getGlobalConfig().options.get(0).killAllDrops && entity instanceof Item) {
                            if (!Util.getItemType(instance.getGlobalConfig().options.get(0).listItemEntitys).contains(((Item) entity).getItemType())) {
                                entity.remove();
                                removedEntities++;
                            }

                            //ClearItems.run(entity,Util.getItemType(instance.getGlobalConfig().options.get(0).listItemEntitys),"WhiteList");

                        }
                        //Kills grouped Animals.
                        if (instance.getGlobalConfig().options.get(0).killAnimalGroups && entity instanceof Animal) {
                            removedEntities = removedEntities + ClearAnimals.run(entity);

                        }
                        if (ClearWhiteList.clear(entity, Util.getEntityType(instance.getGlobalConfig().options.get(0).listEntitys))) {
                            removedEntities++;
                        }
                    }


                }

            }

        }
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

}