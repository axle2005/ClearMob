/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package io.github.axle2005.clearmob;

import com.google.inject.Inject;
import io.github.axle2005.clearmob.clearers.ClearMain;
import io.github.axle2005.clearmob.commands.Register;
import io.github.axle2005.clearmob.configuration.ConfigHandler;
import io.github.axle2005.clearmob.configuration.GlobalConfig;
import io.github.axle2005.clearmob.listeners.ListenersRegister;
import io.github.axle2005.clearmob.util.BroadcastUtil;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Plugin(id = "clearmob", name = "ClearMob")
public class ClearMob {

    private static ClearMob instance;
    @Inject
    private Logger log;
    @Inject
    private PluginContainer pluginContainer;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path defaultConfig;
    private ListenersRegister events;
    private Task.Builder clear = Task.builder();
    private Task.Builder warn = Task.builder();
    private GlobalConfig globalConfig;

    public static ClearMob getInstance() {
        return instance;
    }

    @Listener
    public void onEnable(GameStartedServerEvent event) {
        instance = this;
        events = new ListenersRegister(this);
        new Register(this);
        reload();


        getLogger().info(UpdateChecker.checkRecommended(pluginContainer));


    }

    @Listener
    public void reload(GameReloadEvent event) {
        reload();
    }

    @SuppressWarnings("warningToken")
    public void reload() {
        if (Sponge.getPluginManager().fromInstance(instance).isPresent()) {
            Sponge.getEventManager().unregisterPluginListeners(Sponge.getPluginManager().fromInstance(instance).get());
        }
        clear.reset();
        warn.reset();

        try {
            globalConfig = ConfigHandler.loadConfiguration();


            if (getGlobalConfig().warning.get(0).enabled && getGlobalConfig().passive.get(0).interval > 60) {
                warn = warn.execute(() -> BroadcastUtil.send(getGlobalConfig().warning.get(0).message)).async().delay(getGlobalConfig().passive.get(0).interval - 60, TimeUnit.SECONDS)
                        .interval(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);
                Util.scheduleTask(warn);
            }

            if (getGlobalConfig().passive.get(0).enabled) {

                clear = clear.execute(() -> {
                    ClearMain.run(Sponge.getServer().getConsole());
                    BroadcastUtil.send(getGlobalConfig().passive.get(0).message);
                }).async().delay(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS)
                        .interval(instance.getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);

                Util.scheduleTask(clear);

            }
            if (getGlobalConfig().compressEntities.get(0).enabled) {
                events.registerEvent("Destruct");
            }

            if (getGlobalConfig().mobLimiter.get(0).enabled) {
                events.registerEvent("SpawnEntity");
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

    public GlobalConfig getGlobalConfig() {
        if (globalConfig == null) {
            try {
                globalConfig = ConfigHandler.loadConfiguration();
            } catch (ObjectMappingException | IOException e) {
                log.error("Problem with Config");
            }


        }

        return globalConfig;
    }

}