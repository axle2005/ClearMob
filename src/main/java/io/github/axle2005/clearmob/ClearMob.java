package io.github.axle2005.clearmob;

import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

import io.github.axle2005.clearmob.commands.CommandAdd;
import io.github.axle2005.clearmob.commands.CommandDump;
import io.github.axle2005.clearmob.commands.Commandrun;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "clearmob", name = "ClearMob", version = "1.0.1")
public class ClearMob {

	@Inject
	private Logger log;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	Config config;
	public List<String> listEntities;
	public String listtype;
	private Integer interval;
	private Boolean passive;
	
	Scheduler scheduler = Sponge.getScheduler();
	Task.Builder taskBuilder = scheduler.createTaskBuilder();
	Task task = null;

	@Listener
	public void preInitialization(GamePreInitializationEvent event) {
		config = new Config(this, defaultConfig, configManager);
		listEntities = config.getEntitylist();
		listtype = config.getNodeChildString("Clearing","ListType");
		interval = config.getNodeChildInt("Clearing","Interval");
		passive = config.getNodeChildBoolean("Clearing","PassiveMode");

	}

	@Listener
	public void initialization(GameInitializationEvent event) {
		CommandSpec run = CommandSpec.builder().permission("clearmob.run").description(Text.of("Clear entities"))
				.executor(new Commandrun(this)).build();

		CommandSpec add = CommandSpec.builder().permission("clearmob.add").description(Text.of("Add's Entity to List"))
				.executor(new CommandAdd(this, config)).build();

		CommandSpec dump = CommandSpec.builder().permission("clearmob.dump")
				.description(Text.of("Dump's World Entities to Console/Logs"))
				// .arguments(GenericArguments.string(Text.of("tileentity/entity")))
				.executor(new CommandDump(this)).build();

		CommandSpec clearmob = CommandSpec.builder().description(Text.of("ClearMob Command")).child(run, "run")
				.child(dump, "dump")
				// .child(add, "add")

				.build();

		Sponge.getCommandManager().register(this, clearmob, "ClearMob");
	}

	@Listener
	public void onEnable(GameStartedServerEvent event) {
		
		
		
		passive(passive);
	}

	public void passive(Boolean state) {

		if(state==false && task!=null)
		{
			task.cancel();
		}
		else if(state == true)
		{
			task = taskBuilder
					.execute(() -> Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "clearmob run"))
					.async().intervalTicks(interval * 20).submit(this);
		}
	}

	public Logger getLogger() {
		return log;
	}

	public Path getConfigDir() {
		return defaultConfig;
	}

	@Listener
	public void reload(GameReloadEvent event) {

		listEntities = config.getEntitylist();
		listtype = config.getNodeChildString("Clearing","ListType");
		interval = config.getNodeChildInt("Clearing","Interval");
		passive(config.getNodeChildBoolean("Clearing","PassiveMode"));

	}

}