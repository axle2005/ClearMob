package io.github.axle2005.clearmob.clearers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;

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
                        if (instance.getGlobalConfig().options.get(0).killAllMonsters == true && entity instanceof Monster) {
                            removedEntities++;
                            entity.remove();
                        }
                        //Removes all Drops
                        if (instance.getGlobalConfig().options.get(0).killAllDrops == true && entity instanceof Item) {
                            if(!Util.getItemType(instance.getGlobalConfig().options.get(0).listItemEntitys).contains(((Item) entity).getItemType())){
                                entity.remove();
                                removedEntities++;
                            }

                            //ClearItems.run(entity,Util.getItemType(instance.getGlobalConfig().options.get(0).listItemEntitys),"WhiteList");

                        }
                        //Kills grouped Animals.
                        if (instance.getGlobalConfig().options.get(0).killAnimalGroups == true && entity instanceof Animal) {
                            removedEntities = removedEntities + ClearAnimals.run(entity);

                        }
                        if (ClearWhiteList.clear(entity, Util.getEntityType(instance.getGlobalConfig().options.get(0).listEntitys)) == true) {
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