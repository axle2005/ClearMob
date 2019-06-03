/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
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