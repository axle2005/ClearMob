/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package io.github.axle2005.clearmob.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
@ConfigSerializable
public class OptionsConfig {

    @Setting(value = "Enable Kill All Monsters", comment = "")
    public Boolean killAllMonsters;
    @Setting(value = "Enable Kill All Dropped Items", comment = "")
    public Boolean killAllDrops;
    @Setting(value = "Enable Kill Grouped Animals", comment = "")
    public Boolean killAnimalGroups;
    @Setting(value = "Entity Whitelist", comment = "")
    public List<String> listEntitys;
    @Setting(value = "Tile Entity Whitelist", comment = "")
    public List<String> listTileEntitys;
    @Setting(value = "Dropped Items Blacklist", comment = "")
    public List<String> listItemEntitys;

    void initializeDefaults() {
        killAllMonsters = false;
        killAllDrops = false;
        killAnimalGroups = false;
        listEntitys = new ArrayList<String>(Arrays.asList("minecraft:zombie", "minecraft:witch",
                "minecraft:skeleton", "minecraft:creeper", "minecraft:arrow"));
        listTileEntitys = new ArrayList<String>(Arrays.asList("PlaceHolder"));
        listItemEntitys = new ArrayList<String>(Arrays.asList("minecraft:redstone", "minecraft:diamond"));
    }
}
