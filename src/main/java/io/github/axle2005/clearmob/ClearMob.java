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
import org.spongepowered.api.item.ItemType;
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
    private List<ItemType> listItemType;
    private String itemWB;

    private Integer interval;
    private Integer moblimit;

    private String warningmessage;
    private String clearedmessage;

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
	
	listEntityType = Util.getEntityType(config.getList("ClearingLists,EntityList"));
	listTileEntityType = Util.getTileEntityType(config.getList("Clearing,Lists,TileEntityList"));
	listItemType = Util.getItemType(config.getList("Clearing,Lists,ItemList"));
	itemWB = config.getNodeString("Clearing,KillDrops,ListType");

	configoptions[0] = config.getNodeBoolean("Clearing,PassiveMode");
	configoptions[1] = config.getNodeBoolean("Clearing,KillAllMonsters");
	configoptions[2] = config.getNodeBoolean("Clearing,KillDrops,Enabled");
	configoptions[3] = config.getNodeBoolean("Clearing,KillAnimalGroups");
	configoptions[4] = config.getNodeBoolean("Warning,Enabled");
	configoptions[6] = config.getNodeBoolean("Clearing,MobLimiter,Enabled");
	interval = config.getNodeInt("Clearing,Interval");

	moblimit = config.getNodeInt("Clearing,MobLimiter,Limit");

	warningmessage = config.getNodeString("Warning,Messages,Warning");
	clearedmessage = config.getNodeString("Warning,Messages,Cleared");

    }

    @Listener
    public void initialization(GameInitializationEvent event) {
	new Register(this);
    }

    @Listener
    public void onEnable(GameStartedServerEvent event) {

	worlds = Sponge.getServer().getWorlds();

	clearSubmit(configoptions[0]);
	warnSubmit(configoptions[4]);
	if (configoptions[6] == true) {
	    events.registerEvent("SpawnEntity");
	} else {
	    events.unregisterEvent("SpawnEntity");
	}

    }

    @Listener
    public void reload(GameReloadEvent event) {
	reload();
    }

    public void reload() {

	worlds = Sponge.getServer().getWorlds();
	moblimit = config.getNodeInt("Clearing,MobLimiter,Limit");

	listEntityType = Util.getEntityType(config.getList("ClearingLists,EntityList"));
	listTileEntityType = Util.getTileEntityType(config.getList("Clearing,Lists,TileEntityList"));
	listItemType = Util.getItemType(config.getList("Clearing,Lists,ItemList"));
	itemWB = config.getNodeString("Clearing,KillDrops,ListType");

	configoptions[0] = config.getNodeBoolean("Clearing,PassiveMode");
	configoptions[1] = config.getNodeBoolean("Clearing,KillAllMonsters");
	configoptions[2] = config.getNodeBoolean("Clearing,KillDrops,Enabled");
	configoptions[3] = config.getNodeBoolean("Clearing,KillAnimalGroups");
	configoptions[4] = config.getNodeBoolean("Warning,Enabled");
	configoptions[6] = config.getNodeBoolean("Clearing,MobLimiter,Enabled");
	interval = config.getNodeInt("Clearing,Interval");

	warningmessage = config.getNodeString("Warning,Messages,Warning");
	clearedmessage = config.getNodeString("Warning,Messages,Cleared");

	clearSubmit(configoptions[0]);
	warnSubmit(configoptions[4]);

	if (Sponge.getPluginManager().fromInstance(this).isPresent()) {
	    Sponge.getEventManager().unregisterPluginListeners(Sponge.getPluginManager().fromInstance(this).get());

	    if (configoptions[6] == true) {
		events.registerEvent("SpawnEntity");
	    } else {
		events.unregisterEvent("SpawnEntity");
	    }

	} else {
	    if (configoptions[6] == true) {
		log.warn("Problem unregistering Mob Limiter event");
	    }
	}

	// Unregisters old listeners.
	// Sponge.getEventManager().unregisterPluginListeners(Sponge.getPluginManager().getPlugin("clearmob").get());

    }

    private void clearSubmit(Boolean toggle) {
	build = taskBuilder.execute(() -> {
	    clearing.run(configoptions, listEntityType, Sponge.getServer().getConsole());
	    w.run(clearedmessage);
	}).async().delay(interval, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);

	if (toggle) {
	    if (task == null) {
		task = build.submit(this);
	    } else {

		task.cancel();
		task = build.submit(this);

	    }
	} else {
	    if (task != null) {
		task.cancel();
	    }
	}

    }

    private void warnSubmit(Boolean toggle) {
	warning = taskBuilder.execute(() -> w.run(warningmessage)).async().delay(interval - 60, TimeUnit.SECONDS)
		.interval(interval, TimeUnit.SECONDS);
	if (toggle && configoptions[0]) {
	    if (warn == null) {
		if (interval > 60) {
		    warn = warning.submit(this);
		}

	    } else {
		warn.cancel();
		if (interval > 60) {
		    warn = warning.submit(this);
		}
	    }
	} else {
	    if (warn != null) {
		warn.cancel();
	    }
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

    public List<ItemType> getListItemType() {
	return listItemType;
    }

    public String getitemWB() {
	return itemWB;
    }

    public Collection<World> getWorlds() {
	return worlds;
    }

    public clearMain getClearer() {
	return clearing;
    }

}