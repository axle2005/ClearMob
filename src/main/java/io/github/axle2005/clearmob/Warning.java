package io.github.axle2005.clearmob;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Warning {

	
	public Warning()
	{	
		
		
		
	}
	public void run(String message){
		
		String s = TextSerializers.FORMATTING_CODE.serialize(Text.of(message));
		
		Sponge.getServer().getBroadcastChannel().send(Text.of(s));
		
	}
	
}
