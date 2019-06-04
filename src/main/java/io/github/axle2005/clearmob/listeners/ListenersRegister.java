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

public class ListenersRegister {

    private ClearMob plugin;
    private EntityLimiter entity;
    private ListenerEntityDestruct destruct;

    public ListenersRegister(ClearMob plugin) {
        this.plugin = plugin;
        entity = new EntityLimiter(plugin);
        destruct = new ListenerEntityDestruct(plugin);


    }

    public void registerEvent(String event) {

        if (event.equals("SpawnEntity")) {

            Sponge.getEventManager().registerListeners(plugin, entity);
        }
        if (event.equals("Destruct")) {
            Sponge.getEventManager().registerListeners(plugin, destruct);
        }

    }
}
