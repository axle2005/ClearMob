package io.github.axle2005.clearmob.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MobLimiterConfig {

    public void initializeDefaults(){
        enabled = false;
        limit = 500;
    }
    @Setting(value="Enabled", comment="Set to True to enable feature")
    public Boolean enabled;
    @Setting(value="Max Limit", comment="The max limit of entities that can exist at one time")
    public Integer limit;
}
