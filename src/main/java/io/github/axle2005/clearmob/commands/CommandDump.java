package io.github.axle2005.clearmob.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import io.github.axle2005.clearmob.ClearMob;

public class CommandDump implements CommandExecutor {

	ClearMob plugin;

	public CommandDump(ClearMob plugin) {
		this.plugin = plugin;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		String[] cmdargs = args.toString().split(" ");

		if (src instanceof Player && !src.hasPermission("clearmob.dump")) {
			Player player = (Player) src;
			player.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		}

		else {
			entityDump();
			return CommandResult.success();
		}

	}

	private void entityDump() {
		List<String> listdump = new ArrayList<String>();
		for (World world : Sponge.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {

				if (!listdump.contains("Entity: " + entity.getType().getId())&& !plugin.listEntities.contains(entity.getType().getId())) {
					listdump.add("Entity: " + entity.getType().getId());
				}
								
			}
		}
		for (String s : listdump) {
			plugin.getLogger().info(s);
		}
	}

	private void tileEntityDump() {
		List<String> listdump = new ArrayList<String>();
		for (World world : Sponge.getServer().getWorlds()) {
			for (TileEntity entity : world.getTileEntities()) {

				if (!listdump.contains("Entity: " + entity.getType().getId())) {
					listdump.add("Entity: " + entity.getType().getId());
				}
			}
		}
		for (String s : listdump) {
			plugin.getLogger().info(s);
		}
		
	}

}
