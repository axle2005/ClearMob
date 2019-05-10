package io.github.axle2005.clearmob.configuration;
	

import java.util.ArrayList;
import java.util.List;


import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

    @ConfigSerializable
    public class GlobalConfig {
        public GlobalConfig() {

            options = new ArrayList<>();
            compressEntities= new ArrayList<>();
            passive = new ArrayList<>();
            warning = new ArrayList<>();
            mobLimiter = new ArrayList<>();

        }
        @Setting(value="General Options", comment="These options affect various aspects of the plugin")
        public List<OptionsConfig> options;
        @Setting(value="Mob Compression", comment="Compresses similiar entities into a nice little package")
        public List<CompressEntities> compressEntities;
        @Setting(value="Passive Mode", comment="Passively clears out entities")
        public List<PassiveConfig> passive;
        @Setting(value="Warning Message", comment="Notification to Players")
        public List<WarningConfig> warning;
        @Setting(value="Mob Limiter", comment="Prevents Additional Mobs from Spawning")
        public List<MobLimiterConfig> mobLimiter;

    
}
