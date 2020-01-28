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

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;

import java.util.Collection;
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

        Collection<Entity> eCol = e.getNearbyEntities(3.0);
        for (Entity en : eCol) {
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
