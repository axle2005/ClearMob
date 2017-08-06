package io.github.axle2005.clearmob.util;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class BroadcastUtil {

	public static void send(String message){
		
		Text s = TextSerializers.FORMATTING_CODE.deserialize(TextSerializers.FORMATTING_CODE.serialize(Text.of(message)));
		
		Sponge.getServer().getBroadcastChannel().send(s);
		
	
		
	}
	
}
