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
    public Boolean passive_enabled;
    @Setting(value = "Interval in Seconds", comment = "")
    public Integer interval;
    @Setting(value = "Message to Players", comment = "")
    public String passive_message;
    @Setting(value = "Warning Message Enabled", comment = "")
    public Boolean warning_enabled;
    @Setting(value = "Message to Players", comment = "")
    public String warning_message;

    void initializeDefault() {

        passive_enabled = false;
        interval = 60;
        passive_message = "[ClearMob] Entities have been cleared";
        warning_enabled = false;
        warning_message = "[ClearMob] Clearing Entities in 1 minute";

    }

}
