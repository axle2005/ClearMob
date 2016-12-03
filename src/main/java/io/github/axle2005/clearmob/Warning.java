package io.github.axle2005.clearmob;

import org.spongepowered.api.Sponge;

public class Warning {

	
	public Warning(String message)
	{	
		
		String broadcast = "broadcast "+ message;
		Sponge.getGame().getCommandManager().process(Sponge.getServer().getConsole(), broadcast);
		
	}
	
}
