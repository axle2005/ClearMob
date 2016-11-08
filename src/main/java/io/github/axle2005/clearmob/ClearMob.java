package io.github.axle2005.clearmob;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.world.chunk.LoadChunkEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;

import com.google.inject.Inject;

import io.github.axle2005.clearmob.clearers.clearMain;
import io.github.axle2005.clearmob.clearers.clearTileEntity;
import io.github.axle2005.clearmob.commands.Register;
import io.github.axle2005.clearmob.listeners.CrashChunkClear;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "clearmob", name = "ClearMob", version = "1.1.0")
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

	public Boolean configoptions[] = new Boolean[10];

	public List<String> listEntities;
	public List<String> listTileEntities;
	public String listtype;

	private Integer interval;

	private String warningmessage;

	public Collection<World> worlds;

	Scheduler scheduler = Sponge.getScheduler();
	Task.Builder taskBuilder = scheduler.createTaskBuilder();
	Task task = null;

	Task warn = null;

	@Listener
	public void preInitialization(GamePreInitializationEvent event) {
		config = new Config(this, defaultConfig, configManager);
		listEntities = config.getEntitylist();
		listTileEntities = config.getTilelist();

		configoptions[0] = config.getNodeChildBoolean("Clearing", "PassiveMode");
		configoptions[1] = config.getNodeChildBoolean("Clearing", "KillAllMonsters");
		configoptions[2] = config.getNodeChildBoolean("Clearing", "KillDrops");
		configoptions[3] = config.getNodeChildBoolean("Clearing", "KillAnimalGroups");
		configoptions[4] = config.getNodeChildBoolean("Warning", "Enabled");
		configoptions[5] = config.getNodeChildBoolean("Clearing", "CrashMode");

		listtype = config.getNodeChildString("Clearing", "ListType");
		interval = config.getNodeChildInt("Clearing", "Interval");

		warningmessage = config.getNodeChildString("Warning", "Message");

	}

	@Listener
	public void initialization(GameInitializationEvent event) {
		new Register(this);
	}

	@Listener
	public void onEnable(GameStartedServerEvent event) {
		worlds = Sponge.getServer().getWorlds();
		passive(configoptions[0]);
		// Sponge.getEventManager().registerListener(this,SpawnEntityEvent.class,
		// new EntityLimiter(this));
	}

	@Listener
	public void reload(GameReloadEvent event) {

		reload();

	}

	public void reload() {

		configoptions[0] = config.getNodeChildBoolean("Clearing", "PassiveMode");
		configoptions[1] = config.getNodeChildBoolean("Clearing", "KillAllMonsters");
		configoptions[2] = config.getNodeChildBoolean("Clearing", "KillDrops");
		configoptions[3] = config.getNodeChildBoolean("Clearing", "KillAnimalGroups");
		configoptions[4] = config.getNodeChildBoolean("Warning", "Enabled");
		configoptions[5] = config.getNodeChildBoolean("Clearing", "CrashMode");

		listEntities = config.getEntitylist();
		listTileEntities = config.getTilelist();
		listtype = config.getNodeChildString("Clearing", "ListType");
		interval = config.getNodeChildInt("Clearing", "Interval");

		warningmessage = config.getNodeChildString("Warning", "Message");

		passive(configoptions[0]);

		if (configoptions[5] == true) {
			Sponge.getEventManager().registerListener(this, LoadChunkEvent.class, new CrashChunkClear());
		} else {
			Sponge.getEventManager().unregisterListeners(new CrashChunkClear());
		}

	}

	private void clear(ClearMob clearMob,String listtype, Boolean[] configoptions, List<String> listTileEntities, List<String> listEntities,
			Collection<World> worlds, ConsoleSource consoleSource) {
		new clearTileEntity(clearMob, listTileEntities, worlds, consoleSource);
		new clearMain(clearMob, configoptions, listtype, listEntities, worlds, consoleSource);
	}

	private void passive(Boolean state) {

		if (state == false && task != null) {
			task.cancel();
			if (configoptions[4] == false && warn != null) {
				warn.cancel();
			}
		} else if (state == true) {
			task = taskBuilder.execute(
					() -> clear(this, listtype, configoptions, listTileEntities,listEntities, worlds, Sponge.getServer().getConsole()))

					.async().delay(interval, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS).submit(this);

			if (configoptions[4] == true && interval > 60) {
				warn = taskBuilder.execute(() -> new Warning(warningmessage)).async()
						.delay(interval - 60, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS).submit(this);
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