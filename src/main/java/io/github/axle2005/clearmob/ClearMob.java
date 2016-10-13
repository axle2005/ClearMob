package io.github.axle2005.clearmob;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;


@Plugin(id = "clearmob", name = "ClearMob", version = "1.0")
public class ClearMob {

	@Inject
	private Logger log;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	public Path configDir;
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	public Path defaultConfig;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	public ConfigurationLoader<CommentedConfigurationNode> configManager;
	
	public ConfigurationNode config;
	
	private Basic base;
	
	ConsoleSource src;
	
	CommandSpec command_clear = CommandSpec.builder()
		    .description(Text.of("ClearMob Command"))
		    .permission("clearmob.run")
		    .arguments(
		    		GenericArguments.remainingJoinedStrings(Text.of("run")))
		    .executor(new CommandExec(this))
		    .build();
	
	@Listener
    public void onEnable(GameStartedServerEvent event) {
		src.sendMessage(Text.of("[ClearMob] is enabled"));
		Sponge.getCommandManager().register(this, command_clear, "ClearMob");


		
		
	}
	
	public Logger getLogger() {
	    return log;
	}
	
	public Path getConfigDir() {
		return configDir;
	}
	
	
}
