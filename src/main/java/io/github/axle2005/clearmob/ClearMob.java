package io.github.axle2005.clearmob;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;
import com.google.inject.Inject;

import io.github.axle2005.clearmob.clearers.clearMain;
import io.github.axle2005.clearmob.commands.Register;
import io.github.axle2005.clearmob.listeners.ListenersRegister;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "clearmob", name = "ClearMob")
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

	private List<EntityType> listEntityType;
	private List<TileEntityType> listTileEntityType;

	private Integer interval;
	private Integer moblimit;

	private String warningmessage;

	private Collection<World> worlds;
	private ListenersRegister events;
	private Warning w;
	private clearMain clearing = new clearMain(this);

	Scheduler scheduler = Sponge.getScheduler();
	Task.Builder taskBuilder = scheduler.createTaskBuilder();
	Task task = null;
	Task warn = null;

	Task.Builder warning = null;
	Task.Builder build = null;


	@Listener
	public void preInitialization(GamePreInitializationEvent event) {
		config = new Config(this, defaultConfig, configManager);
		events = new ListenersRegister(this);
		w = new Warning();

		listEntityType = Util.getEntityType(config.getEntitylist());
		listTileEntityType = Util.getTileEntityType(config.getTilelist());

		configoptions[0] = config.getNodeBoolean("Clearing,PassiveMode");
		configoptions[1] = config.getNodeBoolean("Clearing,KillAllMonsters");
		configoptions[2] = config.getNodeBoolean("Clearing,KillDrops");
		configoptions[3] = config.getNodeBoolean("Clearing,KillAnimalGroups");
		configoptions[4] = config.getNodeBoolean("Warning,Enabled");
		configoptions[5] = config.getNodeBoolean("Clearing,CrashMode");
		configoptions[6] = config.getNodeBoolean("Clearing,MobLimiter,Enabled");
		interval = config.getNodeInt("Clearing,Interval");

		moblimit = config.getNodeInt("Clearing,MobLimiter,Limit");

		warningmessage = config.getNodeString("Warning,Message");

	}

	@Listener
	public void initialization(GameInitializationEvent event) {
		new Register(this);
	}

	@Listener
	public void onEnable(GameStartedServerEvent event) {

		worlds = Sponge.getServer().getWorlds();

		if (configoptions[0] == true) {
			if (task == null) {
				build = taskBuilder
						.execute(() -> clearing.run(configoptions, listEntityType,
								Sponge.getServer().getConsole()))
						.async().delay(interval, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);

				task = build.submit(this);
			} else {
				task.cancel();
				build = taskBuilder.execute(
						() -> clearing.run(configoptions, listEntityType, Sponge.getServer().getConsole()))

						.async().delay(interval, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);
				task = build.submit(this);
			}

		}
		if (configoptions[4] == true && configoptions[0] == true && warn == null) {
			if (interval > 60) {
				if (warning == null) {
					warning = taskBuilder.execute(() -> w.run(warningmessage)).async()
							.delay(interval - 60, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);
					warn = warning.submit(this);
				}
			}
		}
		if (configoptions[5] == true) {
			events.registerEvent("Crash");
		} else {
			events.unregisterEvent("Crash");
		}
		if (configoptions[6] == true) {
			events.registerEvent("SpawnEntity");
		} else {
			events.unregisterEvent("SpawnEntity");
		}

	}

	@Listener
	public void reload(GameReloadEvent event) {

		reload();
		// reload();

	}

	public void reload() {

		worlds = Sponge.getServer().getWorlds();
		moblimit = config.getNodeInt("Clearing,MobLimiter,Limit");

		configoptions[0] = config.getNodeBoolean("Clearing,PassiveMode");
		configoptions[1] = config.getNodeBoolean("Clearing,KillAllMonsters");
		configoptions[2] = config.getNodeBoolean("Clearing,KillDrops");
		configoptions[3] = config.getNodeBoolean("Clearing,KillAnimalGroups");
		configoptions[4] = config.getNodeBoolean("Warning,Enabled");
		configoptions[5] = config.getNodeBoolean("Clearing,CrashMode");
		configoptions[6] = config.getNodeBoolean("Clearing,MobLimiter,Enabled");
		interval = config.getNodeInt("Clearing,Interval");

		warningmessage = config.getNodeString("Warning,Message");

		if (configoptions[0] == true) {
			if (task == null) {
				task = build.submit(this);
			} else {
				task.cancel();
				build = taskBuilder.execute(
						() -> clearing.run(configoptions, listEntityType, Sponge.getServer().getConsole()))

						.async().delay(interval, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);
				task = build.submit(this);
			}

		} else {
			if (!(task == null)) {
				task.cancel();
			}
		}
		if (configoptions[4] == true && configoptions[0] == true) {
			if (warn == null) {
				if (interval > 60) {
					if (warning == null) {
						warning = taskBuilder.execute(() -> w.run(warningmessage)).async()
								.delay(interval - 60, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);
					}
					warn = warning.submit(this);
				}

			} else {
				warn.cancel();
				if (interval > 60) {
					warning = taskBuilder.execute(() -> w.run(warningmessage)).async()
							.delay(interval - 60, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);
					warn = build.submit(this);
				}

			}

		} else {
			if (!(warn == null)) {
				warn.cancel();
			}
		}

		if (configoptions[5] == true) {
			events.registerEvent("Crash");
		} else {
			events.unregisterEvent("Crash");
		}
		if (configoptions[6] == true) {
			events.registerEvent("SpawnEntity");
		} else {
			events.unregisterEvent("SpawnEntity");
		}

	}

	public Logger getLogger() {
		return log;
	}

	public Path getConfigDir() {
		return defaultConfig;
	}

	public int getMobLimit() {
		return moblimit;
	}

	public List<EntityType> getListEntityType() {
		return listEntityType;
	}

	public List<TileEntityType> getListTileEntityType() {
		return listTileEntityType;
	}
	
	public Collection<World> getWorlds(){
		return worlds;
	}
	public clearMain getClearer()
	{
		return clearing;
	}

}