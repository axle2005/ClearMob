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

import io.github.axle2005.clearmob.Util;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.world.World;

public class ClearXP {
    private static int removedEntities = 0;

    public static void run(CommandSource src) {

        for (World world : Sponge.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof ExperienceOrb) {
                    entity.remove();
                    removedEntities++;
                }
            }

        }
        Util.feedback("Experience Orbs", src, removedEntities);

    }


}
