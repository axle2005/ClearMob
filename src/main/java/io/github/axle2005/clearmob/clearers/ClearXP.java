package io.github.axle2005.clearmob.clearers;

import io.github.axle2005.clearmob.Util;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.world.World;

public class ClearXP {
    private static int removedEntities = 0;

    public static void run(CommandSource src) {

        for (World world : Sponge.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof ExperienceOrb) {
                    entity.remove();
                    removedEntities++;
                }
            }

        }
        Util.feedback("Experience Orbs", src, removedEntities);

    }


}
