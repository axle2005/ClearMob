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

    @Setting
    public Boolean enabled;
    @Setting
    public Boolean mobs;
    @Setting
    public Boolean animals;
}
