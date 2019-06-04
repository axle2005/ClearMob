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

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandSearch implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) {
        String args = "";
        if (arguments.<String>getOne("tileentity|entity|nearby|all").isPresent()) {
            args = arguments.<String>getOne("tileentity|entity|nearby|all").get();
        }
        if (src instanceof Player && !src.hasPermission("clearmob.dump")) {
            Player player = (Player) src;
            player.sendMessage(Text.of("You do not have permission to use this command!"));
            return CommandResult.empty();
        }

        if (args.equalsIgnoreCase("entity")) {
            // entityDump();
            return CommandResult.success();

        } else {
            src.sendMessage(Text.of("/clearmob <search><entityid>"));
            return CommandResult.empty();
        }
    }

}
