package io.github.axle2005.clearmob.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@SuppressWarnings("all")
@ConfigSerializable
public class PassiveConfig {

    void initializeDefault() {

	enabled = false;
	interval = 60;
	message = "[ClearMob] Entities have been cleared";

    }

    @Setting(value="Enabled", comment="")
    public Boolean enabled;
    @Setting(value="Interval in Seconds", comment="")
    public Integer interval;
    @Setting(value="Message to Players", comment="")
    public String message;

}
