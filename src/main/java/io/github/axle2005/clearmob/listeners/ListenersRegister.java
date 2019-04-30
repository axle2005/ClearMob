package io.github.axle2005.clearmob.listeners;

import org.spongepowered.api.Sponge;
import io.github.axle2005.clearmob.ClearMob;

public class ListenersRegister {

	ClearMob plugin;
	EntityLimiter entity;
	ListenerEntityDestruct destruct;
	public ListenersRegister(ClearMob plugin)
	{
		this.plugin = plugin;
		entity = new EntityLimiter(plugin);
		destruct = new ListenerEntityDestruct(plugin);

		
	}
	public void registerEvent(String event)
	{
		
		if(event.equals("SpawnEntity"))
		{
			
			Sponge.getEventManager().registerListeners(plugin, entity);
		}
		if(event.equals("Destruct")){
			Sponge.getEventManager().registerListeners(plugin, destruct);
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
