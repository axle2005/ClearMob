package io.github.axle2005.clearmob.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class WarningConfig {

    public void initializeDefaults(){
	enabled = false;
	message = "[ClearMob] Clearing Entities in 1 minute";
    }
    
    @Setting
    public Boolean enabled;
    @Setting
    public String message;
    
}
