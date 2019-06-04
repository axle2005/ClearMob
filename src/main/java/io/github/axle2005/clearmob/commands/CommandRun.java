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

package io.github.axle2005.clearmob.commands;


import io.github.axle2005.clearmob.Util;
import io.github.axle2005.clearmob.clearers.ClearEntity;
import io.github.axle2005.clearmob.clearers.ClearTileEntity;
import io.github.axle2005.clearmob.clearers.ClearXP;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class CommandRun implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) {
        String args = "";
        if (arguments.<String>getOne("tileentity|entity|items").isPresent()) {
            args = arguments.<String>getOne("tileentity|entity|items").get();
        }

        switch (args) {
            case "entity": {
                if (arguments.<String>getOne("name").isPresent()) {
                    ClearEntity.run(src, Util.getEntityType(arguments.<String>getOne("name").get()));
                    return CommandResult.success();
                } else {
                    ClearEntity.run(src);
                    return CommandResult.success();
                }

            }
            case "tileentity": {
                if (arguments.<String>getOne("name").isPresent()) {
                    ClearTileEntity.run(src, Util.getTileEntityType(arguments.<String>getOne("name").get()));
                    return CommandResult.success();
                } else {
                    ClearTileEntity.run(src);
                    return CommandResult.success();
                }
            }
            case "items": {

                // Need to fix.
                // ClearItems.run(plugin, items, plugin.getWorlds(), src);
                return CommandResult.success();

            }
            case "xp": {
                ClearXP.run(src);
                return CommandResult.success();

            }
            default: {
                src.sendMessage(Text.of("clearmob <run><tileentity|entity|items|xp>"));
                return CommandResult.empty();
            }
        }

    }

}
