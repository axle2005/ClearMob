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

package io.github.axle2005.clearmob.listeners;

import io.github.axle2005.clearmob.ClearMob;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.living.monster.Boss;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.world.World;

import java.util.List;

public class EntityLimiter {


    private ClearMob plugin;


    public EntityLimiter(ClearMob plugin) {
        this.plugin = plugin;

    }

    @Listener(beforeModifications = true)
    public void handle(SpawnEntityEvent event) {

        List<Entity> entity = event.getEntities();
        Integer count = 0;
        Integer xpcount = 0;

        for (World w : Sponge.getServer().getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e instanceof Monster && !(e instanceof Boss)) {
                    count++;
                }
                if (e instanceof ExperienceOrb) {
                    xpcount++;
                }
            }
        }
        for (int i = 0; i < entity.size(); i++) {
            if (entity.get(i) instanceof Monster && (count > plugin.getMobLimit()) && !(entity.get(i) instanceof Boss)) {

                event.setCancelled(true);
            }
            if ((entity.get(i) instanceof ExperienceOrb && entity.get(i).getNearbyEntities(10).size() > 20)) {
                event.setCancelled(true);
            }
        }


    }

}
