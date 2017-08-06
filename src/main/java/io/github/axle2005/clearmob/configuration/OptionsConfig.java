package io.github.axle2005.clearmob.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.block.tileentity.TileEntityType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;

import io.github.axle2005.clearmob.Util;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class OptionsConfig {
    
    public void initializeDefaults(){
        killAllMonsters = false;
        killAllDrops = false;
        killAnimalGroups = false;
        listEntitys = Util.getEntityType(new ArrayList<String>(Arrays.asList("minecraft:zombie", "minecraft:witch",
    	    "minecraft:skeleton", "minecraft:creeper", "minecraft:arrow")));
        listTileEntitys = Util.getTileEntityType(new ArrayList<String>(Arrays.asList("PlaceHolder")));
        listItemEntitys = Util.getItemType(new ArrayList<String>(Arrays.asList("minecraft:redstone", "minecraft:diamond")));
    }
    
    @Setting
    public Boolean killAllMonsters;
    @Setting
    public Boolean killAllDrops;
    @Setting
    public Boolean killAnimalGroups;
    @Setting
    public List<EntityType> listEntitys;
    @Setting
    public List<TileEntityType> listTileEntitys;
    @Setting
    public List<ItemType> listItemEntitys;
}
