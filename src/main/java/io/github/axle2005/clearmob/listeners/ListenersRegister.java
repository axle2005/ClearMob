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
