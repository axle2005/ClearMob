package io.github.axle2005.clearmob.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import io.github.axle2005.clearmob.ClearMob;

public class Register {
	
	
	
	public Register(ClearMob plugin)
	{
		CommandSpec run = CommandSpec.builder().permission("clearmob.run").description(Text.of("Clear entities"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("tileentity|entity"))))
				.executor(new CommandRun(plugin)).build();
		
		CommandSpec reload = CommandSpec.builder().permission("clearmob.reload").description(Text.of("Clear entities"))
				.executor(new CommandReload(plugin)).build();

		//CommandSpec add = CommandSpec.builder().permission("clearmob.add").description(Text.of("Add's Entity to List"))
		//		.executor(new CommandAdd(plugin, config)).build();

		CommandSpec dump = CommandSpec.builder().permission("clearmob.dump")
				.description(Text.of("Dump's World Entities to Console/Logs"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("tileentity|entity|nearby|all"))))
				.executor(new CommandDump(plugin)).build();
		
		CommandSpec stats = CommandSpec.builder().permission("clearmob.tps")
				.description(Text.of("Provides current stats of server"))
				.executor(new CommandStats()).build();

		CommandSpec clearmob = CommandSpec.builder().description(Text.of("ClearMob Command")).child(run, "run")
				.child(dump, "dump").child(stats, "tps").child(reload, "reload")
				// .child(add, "add")

				.build();

		Sponge.getCommandManager().register(plugin, clearmob, "ClearMob");
	}
	
}
