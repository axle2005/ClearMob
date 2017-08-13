package io.github.axle2005.clearmob;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
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

    private ListenersRegister events;
    Task.Builder clear = Task.builder();
    Task.Builder warn = Task.builder();

    private static ClearMob instance;
    private GlobalConfig globalConfig;
    
    @Listener
    public void onEnable(GameStartedServerEvent event) {
	instance = this;
	reload();
	
	events = new ListenersRegister(this);
	new Register(this);


	
    }

    @Listener
    public void reload(GameReloadEvent event) {
	reload();
    }

    public void reload() {
	Sponge.getEventManager().unregisterPluginListeners(Sponge.getPluginManager().fromInstance(instance).get());

	try {
	    globalConfig = ConfigHandler.loadConfiguration();
	    clear.reset();
	    warn.reset();
	    

	    
	    if(getGlobalConfig().passive.get(0).enabled){
		
		clear = clear.execute(() -> {
		    ClearMain.run(Sponge.getServer().getConsole());
		    BroadcastUtil.send(getGlobalConfig().passive.get(0).message);
		}).async().delay(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS)
			.interval(instance.getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);
		
		Util.scheduleTask(clear);
		
	    }
	    if(getGlobalConfig().warning.get(0).enabled){
		warn = warn.execute(() -> BroadcastUtil.send(getGlobalConfig().warning.get(0).message)).async().delay(getGlobalConfig().passive.get(0).interval- 60, TimeUnit.SECONDS)
			.interval(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);
		Util.scheduleTask(warn);
	    }
	    
	    if (getGlobalConfig().mobLimiter.get(0).enabled == true) {
		events.registerEvent("SpawnEntity");
	    } else {
		//events.unregisterEvent("SpawnEntity");
	    }
		
	} catch (ObjectMappingException | IOException e) {
	    log.error("Problem Reloading Config");
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
    public static ClearMob getInstance() {
	return instance;
    }
    public GlobalConfig getGlobalConfig(){
	return globalConfig;
    }

}