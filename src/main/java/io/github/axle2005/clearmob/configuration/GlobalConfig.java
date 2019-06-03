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
import java.util.List;

@ConfigSerializable
public class GlobalConfig {
    @Setting(value = "General Options", comment = "These options affect various aspects of the plugin")
    public List<OptionsConfig> options;
    @Setting(value = "Mob Compression", comment = "Compresses similiar entities into a nice little package")
    public List<CompressEntities> compressEntities;
    @Setting(value = "Passive Mode", comment = "Passively clears out entities")
    public List<PassiveConfig> passive;
    @Setting(value = "Warning Message", comment = "Notification to Players")
    public List<WarningConfig> warning;
    @Setting(value = "Mob Limiter", comment = "Prevents Additional Mobs from Spawning")
    public List<MobLimiterConfig> mobLimiter;

    @SuppressWarnings("all")
    //Warning: Access can be more private - This is called in the Main Class, which requires public access
    public GlobalConfig() {

        options = new ArrayList<>();
        compressEntities = new ArrayList<>();
        passive = new ArrayList<>();
        warning = new ArrayList<>();
        mobLimiter = new ArrayList<>();

    }


}
