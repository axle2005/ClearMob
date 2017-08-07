package io.github.axle2005.clearmob;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;
import com.google.inject.Inject;

import io.github.axle2005.clearmob.clearers.ClearMain;
import io.github.axle2005.clearmob.commands.Register;
import io.github.axle2005.clearmob.configuration.ConfigHandler;
import io.github.axle2005.clearmob.configuration.GlobalConfig;
import io.github.axle2005.clearmob.listeners.ListenersRegister;
import io.github.axle2005.clearmob.util.BroadcastUtil;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Plugin(id = "clearmob", name = "ClearMob")
public class ClearMob {

    @Inject
    private Logger log;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path defaultConfig;


    private Collection<World> worlds;
    private ListenersRegister events;
    private ClearMain clearing = new ClearMain(this);
    Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
    Task task = null;
    Task warn = null;

    Task.Builder warning = null;
    Task.Builder build = null;

    private static ClearMob instance;
    private GlobalConfig globalConfig;
    
    @Listener
    public void onEnable(GameStartedServerEvent event) {
	instance = this;
	reload();
	
	events = new ListenersRegister(this);
	new Register(this);
	worlds = Sponge.getServer().getWorlds();

	
    }

    @Listener
    public void reload(GameReloadEvent event) {
	reload();
    }

    public void reload() {
	Sponge.getEventManager().unregisterPluginListeners(Sponge.getPluginManager().fromInstance(instance).get());
	worlds = Sponge.getServer().getWorlds();
	try {
	    globalConfig = ConfigHandler.loadConfiguration();
	    
	    clearSubmit(getGlobalConfig().passive.get(0).enabled);
	    warnSubmit(getGlobalConfig().warning.get(0).enabled);
	    
	    if (getGlobalConfig().mobLimiter.get(0).enabled == true) {
		events.registerEvent("SpawnEntity");
	    } else {
		//events.unregisterEvent("SpawnEntity");
	    }
		
	} catch (ObjectMappingException | IOException e) {
	    log.error("Problem Reloading Config");
	}
	   
    }

    private void clearSubmit(Boolean toggle) {
	build = taskBuilder.execute(() -> {
	    clearing.run(Sponge.getServer().getConsole());
	    BroadcastUtil.send(getGlobalConfig().passive.get(0).message);
	}).async().delay(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS).interval(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);

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
	warning = taskBuilder.execute(() -> BroadcastUtil.send(getGlobalConfig().warning.get(0).message)).async().delay(getGlobalConfig().passive.get(0).interval- 60, TimeUnit.SECONDS)
		.interval(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);
	if (toggle && getGlobalConfig().warning.get(0).enabled) {
	    if (warn == null) {
		if (getGlobalConfig().passive.get(0).interval > 60) {
		    warn = warning.submit(this);
		}

	    } else {
		warn.cancel();
		if (getGlobalConfig().passive.get(0).interval > 60) {
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
	return getGlobalConfig().mobLimiter.get(0).limit;
    }
    public Collection<World> getWorlds() {
	return worlds;
    }

    public ClearMain getClearer() {
	return clearing;
    }

    public static ClearMob getInstance() {
	return instance;
    }
    public GlobalConfig getGlobalConfig(){
	return globalConfig;
    }

}