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

import com.google.common.collect.Iterables;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

public class CommandStats implements CommandExecutor {

    private final Text HEADER = Text.builder("-----------------------------------------------").color(TextColors.GRAY)
            .build();

    public CommandResult execute(CommandSource src, CommandContext args) {
        src.sendMessage(this.HEADER);
        double tps = Sponge.getServer().getTicksPerSecond();

        src.sendMessage(Text.builder("Current TPS (" + tps + "):").color(TextColors.GOLD).build());

        src.sendMessage(constructTickbar(tps));

        src.sendMessage(Text.EMPTY);

        src.sendMessage(Text.builder("Worlds:").color(TextColors.GOLD).build());

        for (World w : Sponge.getServer().getWorlds()) {
            src.sendMessage(Text.of("- " + w.getName() + " (" + w.getEntities().size() + " entities, " + w.getTileEntities().size() + " tile, " +
                    +Iterables.size(w.getLoadedChunks()) + " loaded chunks)"));
        }
        src.sendMessage(this.HEADER);
        return CommandResult.success();

    }

    private Text constructTickbar(double tps) {
        Text.Builder TPSText = Text.builder();
        for (int i = 1; i <= 20; i++) {
            if (i <= tps) {
                TPSText.append(new Text[]{Text.builder("=").color(TextColors.GREEN).build()});
            } else {
                TPSText.append(new Text[]{Text.builder("=").color(TextColors.RED).build()});
            }
        }
        return TPSText.build();
    }
}
