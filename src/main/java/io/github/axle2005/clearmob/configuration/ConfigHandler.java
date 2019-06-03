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

