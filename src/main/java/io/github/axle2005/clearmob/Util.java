/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

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
            } else if (s.contains("*")) {
                listEntityType.addAll(Sponge.getRegistry().getAllFor(s.split(":")[0], EntityType.class));
            }
        }

        return listEntityType;
    }

    public static List<TileEntityType> getTileEntityType(List<String> entitys) {
        List<TileEntityType> listEntityType = new ArrayList<TileEntityType>();
        for (String s : entitys) {
            if (Sponge.getRegistry().getType(TileEntityType.class, s).isPresent()) {
                listEntityType.add(Sponge.getRegistry().getType(TileEntityType.class, s).get());
            } else if (s.contains("*")) {
                listEntityType.addAll(Sponge.getRegistry().getAllFor(s.split(":")[0], TileEntityType.class));
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
                if (y.length == 3 || y[1].equals("*")) {
                    s = (y[0] + ":" + y[1]);
                }
            }
            if (Sponge.getRegistry().getType(ItemType.class, s).isPresent()) {
                listEntityType.add(Sponge.getRegistry().getType(ItemType.class, s).get());
            } else if (s.contains("*")) {
                listEntityType.addAll(Sponge.getRegistry().getAllFor(s.split(":")[0], ItemType.class));
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
