package io.github.axle2005.clearmob.listeners;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.world.chunk.LoadChunkEvent;

import io.github.axle2005.clearmob.ClearMob;

public class listenersRegister {

	public listenersRegister(ClearMob plugin,Boolean configoptions[])
	{
		Sponge.getEventManager().registerListener(this,SpawnEntityEvent.class, new EntityLimiter(plugin));
		
		if (configoptions[5] == true) {
			Sponge.getEventManager().registerListener(this, LoadChunkEvent.class, new CrashChunkClear());
		} else {
			Sponge.getEventManager().unregisterListeners(new CrashChunkClear());
		}
		
		
		
		if (configoptions[6] == true) {
			Sponge.getEventManager().registerListener(this,SpawnEntityEvent.class, new EntityLimiter(plugin));
		} else {
			Sponge.getEventManager().unregisterListeners(new EntityLimiter(plugin));
		}
	}
}
