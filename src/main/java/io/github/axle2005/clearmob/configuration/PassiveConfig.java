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

@SuppressWarnings("all")
@ConfigSerializable
public class PassiveConfig {

    @Setting(value = "Enabled", comment = "")
    public Boolean enabled;
    @Setting(value = "Interval in Seconds", comment = "")
    public Integer interval;
    @Setting(value = "Message to Players", comment = "")
    public String message;

    void initializeDefault() {

        enabled = false;
        interval = 60;
        message = "[ClearMob] Entities have been cleared";

    }

}
