package io.github.axle2005.clearmob.configuration;
	

import java.util.ArrayList;
import java.util.List;


import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

    @ConfigSerializable
    public class GlobalConfig {
        public GlobalConfig() {

            passive = new ArrayList<>();
            options = new ArrayList<>();
            warning = new ArrayList<>();
            mobLimiter = new ArrayList<>();

        }

        @Setting
        public List<PassiveConfig> passive;
        @Setting
        public List<OptionsConfig> options;
        @Setting
        public List<WarningConfig> warning;
        @Setting
        public List<MobLimiterConfig> mobLimiter;

    
}
