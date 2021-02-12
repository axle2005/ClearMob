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
import io.github.axle2005.clearmob.clearers.ClearEntity;
import io.github.axle2005.clearmob.commands.Register;
import io.github.axle2005.clearmob.configuration.ConfigHandler;
import io.github.axle2005.clearmob.configuration.GlobalConfig;
import io.github.axle2005.clearmob.listeners.ListenersRegister;
import io.github.axle2005.clearmob.util.BroadcastUtil;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Boss;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private Task tClear;
    private Task tWarn;
    private GlobalConfig globalConfig;


    private final List<String> generalList = new ArrayList<String>(Arrays.asList());
    private final HashMap<EntityType, String> listEntity = new HashMap<EntityType, String>();
    private final HashMap<ItemType, String> listItem = new HashMap<ItemType, String>();
    private final HashMap<TileEntityType, String> listTileEntity = new HashMap<TileEntityType, String>();


    private boolean killEntities = false;
    private boolean killItems = false;


    public static ClearMob getInstance() {
        return instance;
    }

    @Listener
    public void onEnable(GameStartedServerEvent event) {
        instance = this;
        events = new ListenersRegister(this);
        new Register(this);
        reload();


        // getLogger().info(UpdateChecker.checkRecommended(pluginContainer));


    }

    @Listener
    public void reload(GameReloadEvent event) {
        reload();
    }

    public void reload() {

        Sponge.getEventManager().unregisterPluginListeners(Sponge.getPluginManager().getPlugin("clearmob").get());

        if (tClear != null) tClear.cancel();
        if (tWarn != null) tWarn.cancel();
        try {
            globalConfig = ConfigHandler.loadConfiguration();

            listEntity.clear();
            listItem.clear();
            listTileEntity.clear();
            killEntities = false;
            killItems = false;

            String[] sa;
            for (String s : getGlobalConfig().options.get(0).listEntitys) {
                sa = s.split("-");
                    listSort(sa);

            }


            if (getGlobalConfig().passive.get(0).warning_enabled && getGlobalConfig().passive.get(0).interval > 60) {
                warn = warn.execute(() -> BroadcastUtil.send(getGlobalConfig().passive.get(0).warning_message)
                )
                        .delay(getGlobalConfig().passive.get(0).interval - 60, TimeUnit.SECONDS)
                        .interval(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);

                tWarn = warn.submit(this);
                //Util.scheduleTask(warn);
            }

            if (getGlobalConfig().passive.get(0).passive_enabled) {

                clear = clear.execute(() -> {
                    ClearEntity.run(Sponge.getServer().getConsole());
                    BroadcastUtil.send(getGlobalConfig().passive.get(0).passive_message);
                })
                        .delay(getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS)
                        .interval(instance.getGlobalConfig().passive.get(0).interval, TimeUnit.SECONDS);
                tClear = clear.submit(this);
                //Util.scheduleTask(clear);

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

    private void listSort(String[] s) {

        if (s[2].endsWith("*"))
            switch (s[0].toLowerCase()) {
                case "entity":
                    if (s[2].equals("*")) {
                        for (EntityType e : Sponge.getRegistry().getAllOf(EntityType.class)) {
                            if (!listEntity.containsKey(e)) {
                                listEntity.put(e, s[1]);
                            }
                        }


                    } else if (s[2].endsWith("*")) {
                        String mod = (s[2].split(":"))[0];
                        //getLogger().info(mod);
                        for (EntityType e : Sponge.getRegistry().getAllOf(EntityType.class)) {
                            if (e.getId().startsWith(mod)) {
                                listEntity.put(e, s[1]);
                            }
                        }
                    }
                    //listEntity.put(Util.getEntityType(s[2]), s[1]);
                    break;
                case "tileentity":
                    listTileEntity.put(Util.getTileEntityType(s[2]), s[1]);
                    break;
                case "item":
                    if (s[2].equals("*")) {
                        for (ItemType e : Sponge.getRegistry().getAllOf(ItemType.class)) {
                            if (!listItem.containsKey(e)) {
                                listItem.put(e, s[1]);
                            }
                        }

                    } else if (s[2].endsWith("*")) {
                        String mod = (s[2].split(":"))[0];
                        //getLogger().info(mod);
                        for (ItemType e : Sponge.getRegistry().getAllOf(ItemType.class)) {
                            if (e.getId().startsWith(mod)) {
                                listItem.put(e, s[1]);
                            }
                        }
                    }
                    //listEntity.put(Util.getEntityType(s[2]), s[1]);
                    //listItem.put(Util.getItemType(s[2]), s[1]);
                    break;
                default:
                    getLogger().warn("Problem with line: " + s[0] + "-" + s[1] + "-" + s[2]);


            }
        /*
        else */

        if (!s[2].endsWith("*"))
            switch (s[0].toLowerCase()) {
                case "entity":
                    if (s[2].equalsIgnoreCase("boss")) {
                        for (EntityType e : Sponge.getRegistry().getAllOf(EntityType.class)) {
                            if (!listEntity.containsKey(e) && e instanceof Boss) {
                                listEntity.put(e, s[1]);
                            }
                        }

                    } else if (s[2].equalsIgnoreCase("animal")) {
                        for (EntityType e : Sponge.getRegistry().getAllOf(EntityType.class)) {
                            if (!listEntity.containsKey(e) && e instanceof Animal) {
                                listEntity.put(e, s[1]);
                            }
                        }

                    } else if (s[2].equalsIgnoreCase("mob")) {
                        for (EntityType e : Sponge.getRegistry().getAllOf(EntityType.class)) {
                            if (!listEntity.containsKey(e) && e instanceof Monster) {
                                listEntity.put(e, s[1]);
                            }
                        }

                    } else
                        listEntity.put(Util.getEntityType(s[2]), s[1]);
                    break;
                case "tileentity":
                    listTileEntity.put(Util.getTileEntityType(s[2]), s[1]);
                    break;
                case "item":
                    listItem.put(Util.getItemType(s[2]), s[1]);
                    break;
                default:
                    getLogger().warn("Problem with line: " + s[0] + "-" + s[1] + "-" + s[2]);
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

    public List<String> getGeneralList() {
        return generalList;
    }

    public HashMap<EntityType, String> getEntityList() {
        return listEntity;
    }

    public HashMap<ItemType, String> getItemList() {
        return listItem;
    }

    public HashMap<TileEntityType, String> getTileEntityList() {
        return listTileEntity;
    }

    public boolean getKillEntity() {
        return killEntities;
    }

    public boolean getKillItems() {
        return killItems;
    }

}