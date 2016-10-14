package io.github.axle2005.clearmob;

import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "clearmob", name = "ClearMob", version = "1.0")
public class ClearMob {
	
	@Inject
	private Logger log;
  
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	Config config;
	List<String> listEntities;
	//= new ArrayList<String>();

	CommandExec exec;
	ConsoleSource src = Sponge.getServer().getConsole();

	@Listener
	public void preInitialization(GamePreInitializationEvent event) {
		config = new Config(this, defaultConfig, configManager);
		listEntities = config.getEntitylist();

	}

	@Listener
	public void initialization(GameInitializationEvent event) {
		//CommandSpec commandRun = CommandSpec.builder().permission("clearmob.run").executor(new CommandExec(this, this.listEntities)).description(Text.of("ClearMob command")).child(run, "run").build();
		//CommandSpec command_clear = CommandSpec.builder()
		
		exec = new CommandExec(this,listEntities);
		CommandSpec commandRun = CommandSpec.builder().description(Text.of("ClearMob Command"))
				.permission("clearmob.run").arguments(GenericArguments.remainingJoinedStrings(Text.of("run")))
				.executor(exec).build();

		Sponge.getCommandManager().register(this, commandRun, "ClearMob");
	}

	@Listener
	public void onEnable(GameStartedServerEvent event) {
		src.sendMessage(Text.of("[ClearMob] is enabled"));

	}

	public Logger getLogger() {
		return log;
	}

	public Path getConfigDir() {
		return defaultConfig;
	}

	@Listener
	public void reload(GameReloadEvent event) {
		//config.reload();
		listEntities = config.getEntitylist();
		//exec = new CommandExec(this,config.getEntitylist());
		
	}

}