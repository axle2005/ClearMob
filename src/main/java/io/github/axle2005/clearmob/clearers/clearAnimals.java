package io.github.axle2005.clearmob.clearers;

import java.util.ArrayList;
import java.util.Collection;

import org.spongepowered.api.entity.Entity;

public class clearAnimals {
	Collection<Entity> removal;
	public clearAnimals() {
		
	}

	
	
	public void run(Entity e) {
		String name = e.getType().getId();
		int count = 0;
		removal = new ArrayList<Entity>();
			for (Entity en : e.getNearbyEntities(3)) {
				if (!en.isRemoved()) {
					if (en.getType().getId().equals(name)) {
						count++;
						if (count > 20) {
							removal.add(en);
						}
					}

				}
				
			}
			if(!removal.isEmpty())
			{
				for(Entity en : removal)
				{
					if(!en.isRemoved())
					{
						en.remove();
					}
				}
			}
			
		

	}

	

}
