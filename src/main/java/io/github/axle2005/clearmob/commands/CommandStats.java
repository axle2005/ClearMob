package io.github.axle2005.clearmob.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import com.google.common.collect.Iterables;

public class CommandStats implements CommandExecutor {

	private final Text HEADER = Text.builder("-----------------------------------------------").color(TextColors.GRAY)
			.build();

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player && !src.hasPermission("clearmob.tps")) {
			src.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		} else {
			src.sendMessage(this.HEADER);
			double tps = Sponge.getServer().getTicksPerSecond();

			src.sendMessage(Text.builder("Current TPS (" + tps + "):").color(TextColors.GOLD).build());

			src.sendMessage(constructTickbar(tps));

			src.sendMessage(Text.EMPTY);

			src.sendMessage(Text.builder("Worlds:").color(TextColors.GOLD).build());

			for (World w : Sponge.getServer().getWorlds()) {
				src.sendMessage(Text.of("- " + w.getName() + " (" + w.getEntities().size() + " entities, "
						+ Iterables.size(w.getLoadedChunks()) + " loaded chunks)"));
			}
			src.sendMessage(this.HEADER);
			return CommandResult.success();
		}
	}

	public Text constructTickbar(double tps) {
		Text.Builder TPSText = Text.builder();
		for (int i = 1; i <= 20; i++) {
			if (i <= tps) {
				TPSText.append(new Text[] { Text.builder("=").color(TextColors.GREEN).build() });
			} else {
				TPSText.append(new Text[] { Text.builder("=").color(TextColors.RED).build() });
			}
		}
		return TPSText.build();
	}
}
