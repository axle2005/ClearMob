package io.github.axle2005.clearmob;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

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
	
	CommandExec exec = new CommandExec();

	
	@Listener
	public void preInitialization(GameInitializationEvent event){
		log.warn("Registering Commands");
		
		//new CommandExec(this);
		new Basic(this);
		
			
	}
	
	@Listener
    public void onEnable(GameStartedServerEvent event) {
		log.warn(" is enabled");
		//Sponge.getEventManager().registerListeners(this, new EventListener());
		//RawDataChannel channel = Sponge.getGame().getChannelRegistrar().createRawChannel(this, "BungeeCord");
		//Sponge.getCommandManager().register(this, exec, "helloworld", "hello", "test");
		
		
	}
	
	public Logger getLogger() {
	    return log;
	}
	
	public Path getConfigDir() {
		return configDir;
	}
	
	
}
