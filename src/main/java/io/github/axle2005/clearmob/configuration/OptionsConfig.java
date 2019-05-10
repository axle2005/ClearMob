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
    
    @Setting(value="Enable Kill All Monsters", comment="")
    public Boolean killAllMonsters;
    @Setting(value="Enable Kill All Dropped Items", comment="")
    public Boolean killAllDrops;
    @Setting(value="Enable Kill Grouped Animals", comment="")
    public Boolean killAnimalGroups;
    @Setting(value="Entity Whitelist", comment="")
    public List<String> listEntitys;
    @Setting(value="Tile Entity Whitelist", comment="")
    public List<String> listTileEntitys;
    @Setting(value="Dropped Items Blacklist", comment="")
    public List<String> listItemEntitys;
}
