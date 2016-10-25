package io.github.axle2005.clearmob;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;

public class Warning {

	
	public Warning(String message)
	{
		Sponge.getServer().getBroadcastChannel().send(Text.of(message));
	}
	
}
