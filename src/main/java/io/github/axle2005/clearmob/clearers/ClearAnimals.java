package io.github.axle2005.clearmob.clearers;

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClearAnimals {
    private static Map<UUID, Entity> entityData;

    public ClearAnimals() {

    }

    public static int run(Entity e) {
        int count = 0;
        int removed = 0;
        entityData = new ConcurrentHashMap<>();
        for (Entity en : e.getNearbyEntities(3)) {
            if (!en.isRemoved()) {
                if (en.getType().equals(e.getType())) {
                    count++;
                    if (count > 20) {
                        entityData.put(en.getUniqueId(), en);
                    }
                }

            }

        }
        for (Entity en : entityData.values()) {
            if (!en.isRemoved()) {
                if (!en.get(DisplayNameData.class).isPresent()) {
                    // Checks if entity has nametag and ignores it.
                    en.remove();
                    removed++;
                }
            }
        }
        return removed;

    }

}
