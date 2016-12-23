package io.github.axle2005.clearmob.listeners;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.world.chunk.LoadChunkEvent;

import io.github.axle2005.clearmob.ClearMob;

public class ListenersRegister {

	ClearMob plugin;
	EntityLimiter entity;
	CrashChunkClear clear;
	public ListenersRegister(ClearMob plugin)
	{
		this.plugin = plugin;
		entity = new EntityLimiter(plugin);
		clear = new CrashChunkClear();
		
	}
	public void registerEvent(String event)
	{
		
		if(event.equals("SpawnEntity"))
		{
			Sponge.getEventManager().registerListener(plugin,SpawnEntityEvent.class, entity);
		}
		if(event.equals("Crash"))
		{
			Sponge.getEventManager().registerListener(plugin,LoadChunkEvent.class, clear);
		}
		
	}
	public void unregisterEvent(String event)
	{
		if(event.equals("SpawnEntity"))
		{
			Sponge.getEventManager().unregisterListeners(entity);
		}
		if(event.equals("Crash"))
		{
			Sponge.getEventManager().unregisterListeners(clear);
		}
		
	}
}
