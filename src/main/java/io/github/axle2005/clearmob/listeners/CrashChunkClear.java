package io.github.axle2005.clearmob.listeners;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.world.chunk.LoadChunkEvent;
import org.spongepowered.api.text.Text;

public class CrashChunkClear implements EventListener<LoadChunkEvent> {

	@Override
	public void handle(LoadChunkEvent event) throws Exception {
		int removedentities=0;
		for (Entity entity : event.getTargetChunk().getEntities()) {
			if ((!entity.getType().getId().equalsIgnoreCase("minecraft:player")))
			{
				entity.remove();
				removedentities++;
			}

		}
		Sponge.getServer().getBroadcastChannel().send(Text.of("Removed: "+removedentities+" Entities at chunk: "+event.getTargetChunk().getPosition()));
	}

}
