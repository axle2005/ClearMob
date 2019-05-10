package io.github.axle2005.clearmob.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class CompressEntities {

    public void initializeDefault() {

        enabled = false;
        mobs = false;
        animals = false;

    }

    @Setting(value="Enabled", comment="")
    public Boolean enabled;
    @Setting(value="Enable for Monsters", comment="")
    public Boolean mobs;
    @Setting(value="Enable for Animals", comment="")
    public Boolean animals;
}
