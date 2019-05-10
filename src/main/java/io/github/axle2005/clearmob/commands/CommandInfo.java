package io.github.axle2005.clearmob.commands;

import io.github.axle2005.clearmob.ClearMob;
import io.github.axle2005.clearmob.Util;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Collection;

public class CommandInfo implements CommandExecutor {

    ClearMob plugin;

    public CommandInfo(ClearMob plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext arguments) {

        String type = arguments.<String>getOne("range/hand").get();

        if (!Util.playerPermCheck(src, "clearmob.admin")) {
            return CommandResult.empty();
        } else if (src instanceof Player) {
            Player player = (Player) src;

            if (type.equalsIgnoreCase("hand")) {
                player.sendMessage(Text.of(player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getId()));

                return CommandResult.success();
            } else if (type.equalsIgnoreCase("range")) {
                Integer range = arguments.<Integer>getOne("range").get();
                Collection<Entity> e = player.getNearbyEntities(range);

                for (Entity en : e) {
                    plugin.getLogger().info("Entity: " + en.getType().getId());
                    plugin.getLogger().info("Name " + en.getType().getName());
                    plugin.getLogger().info("DisplayName " + en.getContainers());
                    plugin.getLogger().info("Display " + en.getValues());

                    if (en.getKeys().contains(Keys.DISPLAY_NAME)) {
                        plugin.getLogger().info("D " + en.get(Keys.DISPLAY_NAME));
                    }


                }
                return CommandResult.success();
            } else {
                src.sendMessage(Text.of("clearmob <info><range|hand>"));
                return CommandResult.empty();
            }

        } else {
            src.sendMessage(Text.of("You can not run this command"));
            return CommandResult.empty();
        }

    }

}