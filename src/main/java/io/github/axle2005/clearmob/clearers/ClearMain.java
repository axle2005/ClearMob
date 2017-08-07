package io.github.axle2005.clearmob.clearers;

import org.spongepowered.api.command.CommandSource;

public class ClearMain {
    
    public static void run(CommandSource src) {
	ClearEntity.run(src);
	ClearTileEntity.run(src);

    }


}
