package io.github.axle2005.clearmob;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
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
import com.google.inject.Inject;

import io.github.axle2005.clearmob.commands.Register;
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
	public Boolean killmobs;
	private Integer interval;
	private Boolean passive;
	
	private Boolean warning;
	private String warningmessage;

	Scheduler scheduler = Sponge.getScheduler();
	Task.Builder taskBuilder = scheduler.createTaskBuilder();
	Task task = null;
	
	Task warn = null;

	@Listener
	public void preInitialization(GamePreInitializationEvent event) {
		config = new Config(this, defaultConfig, configManager);
		listEntities = config.getEntitylist();
		listtype = config.getNodeChildString("Clearing", "ListType");
		interval = config.getNodeChildInt("Clearing", "Interval");
		passive = config.getNodeChildBoolean("Clearing", "PassiveMode");
		killmobs = config.getNodeChildBoolean("Clearing","KillAllMonsters");
		
		warning = config.getNodeChildBoolean("Warning", "Enabled");
		warningmessage = config.getNodeChildString("Warning", "Message");

	}

	@Listener
	public void initialization(GameInitializationEvent event) {
		new Register(this);
	}

	@Listener
	public void onEnable(GameStartedServerEvent event) {
		passive(passive);
	}

	@Listener
	public void reload(GameReloadEvent event) {

		reload();

	}

	public void reload() {

		listEntities = config.getEntitylist();
		listtype = config.getNodeChildString("Clearing", "ListType");
		interval = config.getNodeChildInt("Clearing", "Interval");
		passive(config.getNodeChildBoolean("Clearing", "PassiveMode"));
		killmobs = config.getNodeChildBoolean("Clearing","KillAllMonsters");
		warning = config.getNodeChildBoolean("Warning", "Enabled");
		warningmessage = config.getNodeChildString("Warning", "Message");
		
		

	}

	public void passive(Boolean state) {

		if (state == false && task != null) {
			task.cancel();
			if(warning == false && warn != null)
			{
				warn.cancel();
			}
		} else if (state == true) {
			task = taskBuilder
					.execute(() -> Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "clearmob run"))
					.async()
		            .delay(interval, TimeUnit.SECONDS)
		            .interval(interval, TimeUnit.SECONDS)
		            .submit(this);
			
			if(warning == true && interval > 60)
			{
				warn = taskBuilder
						.execute(() -> new Warning(warningmessage))
						.async()
			            .delay(interval-60, TimeUnit.SECONDS)
			            .interval(interval, TimeUnit.SECONDS)
			            .submit(this);
			}
		}
		
	}

	public Logger getLogger() {
		return log;
	}

	public Path getConfigDir() {
		return defaultConfig;
	}

}