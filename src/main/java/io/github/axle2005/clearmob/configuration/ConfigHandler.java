package io.github.axle2005.clearmob.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.google.common.reflect.TypeToken;

import io.github.axle2005.clearmob.ClearMob;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigHandler {
    
    public static GlobalConfig loadConfiguration() throws ObjectMappingException, IOException {
        ClearMob instance = ClearMob.getInstance();
        Logger logger = instance.getLogger();
        Path configDir = instance.getConfigDir();

        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir);
        }

        Path configFile = Paths.get(configDir + "/ClearMob.json");

        GsonConfigurationLoader configLoader = GsonConfigurationLoader.builder().setPath(configFile).build();
        ConfigurationNode configNode = configLoader.load();

        GlobalConfig config = configNode.getValue(TypeToken.of(GlobalConfig.class), new GlobalConfig());

        if (!Files.exists(configFile)) {
            Files.createFile(configFile);
            logger.info("Created default configuration!");

            PassiveConfig passive = new PassiveConfig();
            passive.initializeDefault();
            
            WarningConfig warning = new WarningConfig();
            warning.initializeDefaults();
            
            OptionsConfig options = new OptionsConfig();
            options.initializeDefaults();
            
            MobLimiterConfig mobLimiter = new MobLimiterConfig();
            mobLimiter.initializeDefaults();
            
            config.passive.add(passive);
            config.warning.add(warning);
            config.options.add(options);
            config.mobLimiter.add(mobLimiter);
        }


        configNode.setValue(TypeToken.of(GlobalConfig.class), config);
        configLoader.save(configNode);
        logger.info("Configuration loaded.");


        return config;
    }
    
}

