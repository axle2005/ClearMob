package io.github.axle2005.clearmob;


import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;



public class CommandExec implements CommandExecutor {

	ClearMob plugin;
	
	CommandSpec myCommandSpec = CommandSpec.builder()
		    .description(Text.of("Run Mob Cleaner"))
		    .permission("MobCleaner.command.run")
		    .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
		    .executor(this)
		    .build();
	
	
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
