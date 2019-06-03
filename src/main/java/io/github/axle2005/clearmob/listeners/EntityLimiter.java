/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
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
