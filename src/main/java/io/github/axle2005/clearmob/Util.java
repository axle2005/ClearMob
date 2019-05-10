package io.github.axle2005.clearmob;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.scheduler.Task.Builder;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {


    public static void feedback(String type, CommandSource src, Integer removed) {
        ClearMob instance = ClearMob.getInstance();
        String message = removed + " " + type + " were removed";
        instance.getLogger().info(message);
        if (src instanceof Player) {
            src.sendMessage(Text.of(message));
        }
    }

    public static List<EntityType> getEntityType(List<String> entitys) {
        List<EntityType> listEntityType = new ArrayList<>(Arrays.asList(EntityTypes.PRIMED_TNT));
        for (String s : entitys) {
            if (Sponge.getRegistry().getType(EntityType.class, s).isPresent()) {
                listEntityType.add(Sponge.getRegistry().getType(EntityType.class, s).get());
            }
        }

        return listEntityType;
    }

    public static List<TileEntityType> getTileEntityType(List<String> entitys) {
        List<TileEntityType> listEntityType = new ArrayList<TileEntityType>();
        for (String s : entitys) {
            if (Sponge.getRegistry().getType(TileEntityType.class, s).isPresent()) {
                listEntityType.add(Sponge.getRegistry().getType(TileEntityType.class, s).get());
            }
        }

        return listEntityType;
    }

    public static List<ItemType> getItemType(List<String> entitys) {
        List<ItemType> listEntityType = new ArrayList<ItemType>();
        for (String s : entitys) {

            //I don't even remember why I did this... variants maybe? modded blocks?
            if (s.contains(":")) {
                String[] y = s.split(":");
                if (y.length == 3) {
                    s = (y[0] + ":" + y[1]);

                }
            }
            if (Sponge.getRegistry().getType(ItemType.class, s).isPresent()) {
                listEntityType.add(Sponge.getRegistry().getType(ItemType.class, s).get());
            }
        }

        return listEntityType;
    }

    public static EntityType getEntityType(String s) {
        return Sponge.getRegistry().getType(EntityType.class, s).get();

    }

    public static TileEntityType getTileEntityType(String s) {
        return Sponge.getRegistry().getType(TileEntityType.class, s).get();

    }

    public static Boolean playerPermCheck(CommandSource src, String perm) {
        if (src instanceof Player && !src.hasPermission(perm)) {
            src.sendMessage(Text.of("You are missing: " + perm + ", and can not run this."));
            return false;
        } else
            return true;
    }

    static void scheduleTask(Builder build) {
        ClearMob instance = ClearMob.getInstance();
        build.submit(instance);

    }


}
