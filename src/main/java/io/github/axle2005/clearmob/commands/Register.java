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

import io.github.axle2005.clearmob.ClearMob;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Register {

    public Register(ClearMob plugin) {
        CommandSpec run = CommandSpec.builder().permission("clearmob.admin").description(Text.of("Clear entities"))
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("tileentity|entity|items"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name"))))
                .executor(new CommandRun()).build();

        CommandSpec reload = CommandSpec.builder().permission("clearmob.admin").description(Text.of("Clear entities"))
                .executor(new CommandReload(plugin)).build();

        // CommandSpec add =
        // CommandSpec.builder().permission("clearmob.add").description(Text.of("Add's
        // Entity to List"))
        // .executor(new CommandAdd(plugin, config)).build();

        CommandSpec dump = CommandSpec.builder().permission("clearmob.admin")
                .description(Text.of("Dump's World Entities to Console/Logs"))
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("tileentity|entity|nearby|all"))))
                .executor(new CommandDump()).build();

        CommandSpec info = CommandSpec.builder().permission("clearmob.info")
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("range/hand"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("range"))))
                .description(Text.of("Gets the Item Id of the item in hand. ")).executor(new CommandInfo(plugin))
                .build();

        CommandSpec stats = CommandSpec.builder().permission("clearmob.tps")
                .description(Text.of("Provides current stats of server")).executor(new CommandStats()).build();

        CommandSpec chunks = CommandSpec.builder().permission("clearmob.admin")
                .description(Text.of("Unloads Excess Chunks")).executor(new CommandClearChunks()).build();

        CommandSpec clearmob = CommandSpec.builder().description(Text.of("ClearMob Command")).child(run, "run")
                .child(dump, "dump").child(stats, "tps").child(reload, "reload").child(info, "info").child(chunks, "unloadchunks")
                // .child(add, "add")

                .build();

        Sponge.getCommandManager().register(plugin, clearmob, "clearMob");

    }

}
