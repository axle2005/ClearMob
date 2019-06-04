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
