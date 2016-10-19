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
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	Config config;
	public List<String> listEntities;
	//= new ArrayList<String>();

	@Listener
	public void preInitialization(GamePreInitializationEvent event) {
		config = new Config(this, defaultConfig, configManager);
		listEntities = config.getEntitylist();

	}

	@Listener
	public void initialization(GameInitializationEvent event) {
		CommandSpec run = CommandSpec.builder()
				.permission("clearmob.run")
		        .description(Text.of("Clear entities"))
		        .executor(new Commandrun(this))

		        .build();
		
		CommandSpec clearmob = CommandSpec.builder()
		        .description(Text.of("ClearMob Command"))
		        .child(run, "run")

		        .build();
		
		Sponge.getCommandManager().register(this, clearmob, "ClearMob");
	}

	@Listener
	public void onEnable(GameStartedServerEvent event) {
		
		
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
		
	}

}