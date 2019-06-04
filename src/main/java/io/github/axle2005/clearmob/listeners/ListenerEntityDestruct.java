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
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.type.Exclude;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ListenerEntityDestruct {

    private ClearMob plugin;

    public ListenerEntityDestruct(ClearMob plugin) {
        this.plugin = plugin;
    }

    @Listener(order = Order.EARLY)
    @Exclude(DestructEntityEvent.class)
    public void onBlockPlace(SpawnEntityEvent event) {
        if (!event.getCause().contains(Sponge.getPluginManager().getPlugin("clearmob").get())) {
            if (!event.getEntities().isEmpty()) {
                Entity e = event.getEntities().get(0);
                int x = 1;
                if (e instanceof Monster && plugin.getGlobalConfig().compressEntities.get(0).mobs) {
                    EntityType eType = e.getType();

                    for (Entity eNear : e.getNearbyEntities(10)) {
                        if (eNear.getType().equals(eType) && !eNear.isRemoved()) {
                            if (eNear.get(Keys.DISPLAY_NAME).isPresent()) {
                                x = x + getValueFromName(eNear);


                            } else {
                                x = x + 1;
                            }

                            eNear.offer(Keys.VANISH, true);
                            eNear.remove();
                        }

                    }
                    if (x > 1) {
                        e.offer(Keys.DISPLAY_NAME, Text.of(x + "x"));
                        e.offer(Keys.CUSTOM_NAME_VISIBLE, true);
                    }

                }

            }
        }


    }

    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event) {
        if (!(event.getTargetEntity() instanceof Player)) {
            if (event.getTargetEntity().get(Keys.DISPLAY_NAME).isPresent()) {
                int x = getValueFromName(event.getTargetEntity());
                if (x > 1) {
                    x = x - 1;
                    Location<World> eLoc = event.getTargetEntity().getLocation();
                    Entity e = eLoc.createEntity(event.getTargetEntity().getType());
                    e.offer(Keys.CUSTOM_NAME_VISIBLE, true);
                    e.offer(Keys.DISPLAY_NAME, Text.of(x + "x"));

                    eLoc.spawnEntity(e);

                }
            }
        }
    }

    @SuppressWarnings("all")
    private Integer getValueFromName(Entity entity) {
        //Warning: .isPresent() is checked when this method is called.
        Text text = entity.get(Keys.DISPLAY_NAME).get();
        int x = 1;
        if (text.toPlain().contains("x")) {
            x = Integer.valueOf(text.toPlain().replace('x', ' ').trim());
        }
        return x;

    }
}
