package io.github.axle2005.clearmob;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;






public class CommandExec implements CommandExecutor {

	ClearMob plugin;
	

	
	CommandExec(ClearMob plugin) {
		this.plugin = plugin;

	}
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		//String cmd_args[] = args.toString().split(" ");
		String cmd_args = args.<String>getOne("run").get();
		//String cmd_args = args.toString();
			if(src instanceof Player) {
				Player player = (Player) src;
			    if (!src.hasPermission("clearmob.run")) {
			    	
			    	player.sendMessage(Text.of("You do not have permission to use this command!"));
			    	return null;
			      }
			    else
			    {
			    	if(cmd_args.equalsIgnoreCase("run"))
			    	{
			    		plugin.getLogger().info("Working");
			    		return CommandResult.success();
			    	}
			    }
			    
			   // player.sendMessage(Text.of("Hello " + player.getName() + "!"));
			}
			else if(src instanceof ConsoleSource) {
		    	if(cmd_args.equalsIgnoreCase("run"))
		    	{
    		
		    		Optional<World> world = Sponge.getServer().getWorld("world");
		    		Collection<Entity> entities = world.get().getEntities();
		    		int amount = entities.size();
		    		Entity[] entityArray = entities.toArray(new Entity[amount]);
		    		
		    	
		    		   		
		    		
		    		
		    		for(int i = 0; i <= amount-1;i++){
		    			
		    			entityArray[i].remove();
		    		}
		    		src.sendMessage(Text.of("[ClearMob] " + amount+ " entities were removed"));
		    		
		    		return CommandResult.success();
		    	}
			}
		

		
		//CommandResult result = CommandResult.builder()
		//	    .affectedEntities(42)
		//	    .successCount(1)
		//	    .build();
		
		
		return CommandResult.empty();
	}
	
	
	
}
