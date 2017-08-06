package io.github.axle2005.clearmob.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class PassiveConfig {

    public PassiveConfig() {

    }

    public void initializeDefault() {

	enabled = false;
	interval = 60;
	message = "[ClearMob] Entities have been cleared";

    }

    @Setting
    public Boolean enabled;
    @Setting
    public Integer interval;
    @Setting
    public String message;

}
