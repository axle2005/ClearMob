package io.github.axle2005.clearmob.clearers;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;

import java.util.List;

public class ClearWhiteList {

    public ClearWhiteList() {

    }

    static Boolean clear(Entity entity, List<EntityType> list) {

        for (int i = 0; i <= list.size() - 1; i++) {

            if ((entity.getType().equals(list.get(i)))) {
                entity.remove();
                return true;

            }

        }
        return false;


    }
}
