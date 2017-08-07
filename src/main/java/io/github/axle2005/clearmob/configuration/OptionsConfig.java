package io.github.axle2005.clearmob.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class OptionsConfig {
    
    public void initializeDefaults(){
        killAllMonsters = false;
        killAllDrops = false;
        killAnimalGroups = false;
        listEntitys = new ArrayList<String>(Arrays.asList("minecraft:zombie", "minecraft:witch",
    	    "minecraft:skeleton", "minecraft:creeper", "minecraft:arrow"));
        listTileEntitys = new ArrayList<String>(Arrays.asList("PlaceHolder"));
        listItemEntitys = new ArrayList<String>(Arrays.asList("minecraft:redstone", "minecraft:diamond"));
    }
    
    @Setting
    public Boolean killAllMonsters;
    @Setting
    public Boolean killAllDrops;
    @Setting
    public Boolean killAnimalGroups;
    @Setting
    public List<String> listEntitys;
    @Setting
    public List<String> listTileEntitys;
    @Setting
    public List<String> listItemEntitys;
}
