package io.github.axle2005.clearmob.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MobLimiterConfig {

    public void initializeDefaults(){
        enabled = false;
        limit = 500;
    }
    @Setting
    public Boolean enabled;
    @Setting
    public Integer limit;
}
