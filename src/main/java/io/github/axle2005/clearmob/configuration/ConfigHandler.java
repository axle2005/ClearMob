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

package io.github.axle2005.clearmob.configuration;

import com.google.common.reflect.TypeToken;
import io.github.axle2005.clearmob.ClearMob;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigHandler {

    public static GlobalConfig loadConfiguration() throws ObjectMappingException, IOException {
        ClearMob instance = ClearMob.getInstance();

        Path configDir = instance.getConfigDir();

        //Creates ClearMob folder in the 'plugins' directory defined in sponge global.conf
        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir);
        }


        Path configFile = Paths.get(configDir + "/ClearMob.json");
        GsonConfigurationLoader configLoader = GsonConfigurationLoader.builder().setPath(configFile).build();

        ConfigurationNode configNode = configLoader.load();

        GlobalConfig config = configNode.getValue(TypeToken.of(GlobalConfig.class), new GlobalConfig());

        //Creates Clearmob.json if it does not exist
        if (!Files.exists(configFile)) {
            Files.createFile(configFile);
            instance.getLogger().info("Created default configuration!");
        }


        //Adds default entries for Options
        if (config.options.isEmpty()) {
            OptionsConfig options = new OptionsConfig();
            options.initializeDefaults();
            config.options.add(options);
        }
        //Adds default entries for Mob Limiter
        if (config.mobLimiter.isEmpty()) {
            MobLimiterConfig mobLimiter = new MobLimiterConfig();
            mobLimiter.initializeDefaults();
            config.mobLimiter.add(mobLimiter);
        }
        //Adds default entries for Compression mode
        if (config.compressEntities.isEmpty()) {
            CompressEntities compressEntities = new CompressEntities();
            compressEntities.initializeDefault();
            config.compressEntities.add(compressEntities);
        }


        //Adds default entries for Passive mode
        if (config.passive.isEmpty()) {
            PassiveConfig passive = new PassiveConfig();
            passive.initializeDefault();
            config.passive.add(passive);
        }
        //Adds default entries for Warning Message
        if (config.warning.isEmpty()) {
            WarningConfig warning = new WarningConfig();
            warning.initializeDefaults();
            config.warning.add(warning);
        }

        configNode.setValue(TypeToken.of(GlobalConfig.class), config);
        configLoader.save(configNode);
        instance.getLogger().info("Configuration loaded.");


        return config;
    }

}

