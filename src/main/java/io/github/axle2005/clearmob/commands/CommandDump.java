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
import io.github.axle2005.clearmob.Util;

public class CommandDump implements CommandExecutor {

	ClearMob plugin;
	Util util = new Util(plugin);
	public CommandDump(ClearMob plugin) {
		this.plugin = plugin;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext arguments) throws CommandException {
		String args = arguments.getOne("tileentity|entity").toString();
		if (src instanceof Player && !src.hasPermission("clearmob.dump")) {
			Player player = (Player) src;
			player.sendMessage(Text.of("You do not have permission to use this command!"));
			return CommandResult.empty();
		}

		else {
			if (args.equalsIgnoreCase("Optional[tileentity]")) {
				args = "tileentity";
			}
			else if(args.equalsIgnoreCase("Optional[entity]"))
			{
				args = "entity";
			}
			if(args.equalsIgnoreCase("entity"))
			{
				entityDump();
				return CommandResult.success();
			}
			else if(args.equalsIgnoreCase("tileentity"))
			{
				tileEntityDump();
				return CommandResult.success();
			}
			else
			{
				src.sendMessage(Text.of("/clearmob <dump><tileentity|entity>"));
				return CommandResult.empty();
			}
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
		if(listdump.isEmpty())
		{
			plugin.getLogger().info("No Entities to Add");
		}
		else
		{
			for (String s : listdump) {
				plugin.getLogger().info(s);
			}
		}
		
		
	}

	private void tileEntityDump() {
		List<String> listdump = new ArrayList<String>();
		for (World world : Sponge.getServer().getWorlds()) {
			for (TileEntity entity : world.getTileEntities()) {

				if (!listdump.contains("Entity: " + entity.getType().getId())&& !plugin.listEntities.contains(entity.getType().getId())) {
					listdump.add("Entity: " + entity.getType().getId());
				}
			}
		}
		if(listdump.isEmpty())
		{
			plugin.getLogger().info("No Tile Entities to Add");
		}
		else
		{
			for (String s : listdump) {
				plugin.getLogger().info(s);
			}
		}
	}

}
