package io.github.axle2005.clearmob.commands;

import io.github.axle2005.clearmob.ClearMob;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandReload implements CommandExecutor {

    private ClearMob plugin;

    public CommandReload(ClearMob plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        plugin.reload();
        plugin.getLogger().info("Reloaded Successfully");
        if (src instanceof Player) {
            src.sendMessage(Text.of("Reload complete"));
        }
        return CommandResult.success();

    }

}
