/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package io.github.axle2005.clearmob;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.scheduler.Task.Builder;
import org.spongepowered.api.text.Text;

public class Util {


    public static void feedback(String type, CommandSource src, Integer removed) {
        ClearMob instance = ClearMob.getInstance();
        String message = removed + " " + type + " were removed";
        instance.getLogger().info(message);
        if (src instanceof Player) {
            src.sendMessage(Text.of(message));
        }
    }

    public static EntityType getEntityType(String s) {
        return Sponge.getRegistry().getType(EntityType.class, s).get();

    }

    public static TileEntityType getTileEntityType(String s) {
        return Sponge.getRegistry().getType(TileEntityType.class, s).get();

    }

    public static ItemType getItemType(String s) {
        return Sponge.getRegistry().getType(ItemType.class, s).get();

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
