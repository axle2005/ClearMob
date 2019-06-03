/*
 *   Copyright (c) 2019 Ryan Arnold (Axle)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
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
