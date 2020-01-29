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
import java.util.List;

@ConfigSerializable
public class GlobalConfig {
    @Setting(value = "General Options", comment = "These options affect various aspects of the plugin")
    public List<OptionsConfig> options;
    @Setting(value = "Passive Mode", comment = "Passively clears out entities")
    public List<PassiveConfig> passive;
    @Setting(value = "Mob Compression", comment = "Compresses similiar entities into a nice little package")
    public List<CompressEntities> compressEntities;
    @Setting(value = "Mob Limiter", comment = "Prevents Additional Mobs from Spawning")
    public List<MobLimiterConfig> mobLimiter;

    @SuppressWarnings("all")
    //Warning: Access can be more private - This is called in the Main Class, which requires public access
    public GlobalConfig() {

        options = new ArrayList<>();
        passive = new ArrayList<>();
        compressEntities = new ArrayList<>();
        mobLimiter = new ArrayList<>();

    }


}
