package io.github.axle2005.clearmob.listeners;

import org.spongepowered.api.Sponge;
import io.github.axle2005.clearmob.ClearMob;

public class ListenersRegister {

	ClearMob plugin;
	EntityLimiter entity;
	public ListenersRegister(ClearMob plugin)
	{
		this.plugin = plugin;
		entity = new EntityLimiter(plugin);
		
	}
	public void registerEvent(String event)
	{
		
		if(event.equals("SpawnEntity"))
		{
			
			Sponge.getEventManager().registerListeners(plugin, entity);
		}
		
	}
	public void unregisterEvent(String event)
	{
		if(event.equals("SpawnEntity"))
		{
			
			Sponge.getEventManager().unregisterListeners(entity);
		}
		
	}
}
