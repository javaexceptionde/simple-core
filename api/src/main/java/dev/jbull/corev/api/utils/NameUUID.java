/*
 * Copyright  (c) 2021.  Jonathan Bull Contact at jonathan@jbull.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jbull.corev.api.utils;

import dev.jbull.corev.Core;
import dev.jbull.corev.api.CoreV;
import dev.jbull.corev.player.nameUuid.INameUuidFetcher;
import dev.jbull.corev.player.nameUuid.NameUuidFetcher;

import java.util.UUID;

public class NameUUID  {
    private NameUuidFetcher nameUuidFetcher;

    /**
     * Gets the name of the Specified User from the Database by the given UUID
     * @param uuid the UUID of the player you want to get the Name
     * @return Returns the name of the player by his UUID
     */
    public String getName(UUID uuid) {
        return nameUuidFetcher.getName(uuid);
    }

    /**
     * Gets the UUID of the Specified User from the Database by the given Name
     * @param name the Name of the Player you want to get the UUID
     * @return Returns the UUID of the player by his UUID
     */
    public UUID getUUID(String name) {
        return nameUuidFetcher.getUUID(name);
    }

    public boolean hasPlayedOnNetwork(UUID uuid) {
        return nameUuidFetcher.hasPlayedOnNetwork(uuid);
    }

    public boolean hasPlayedOnNetwork(String name) {
        return nameUuidFetcher.hasPlayedOnNetwork(name);
    }

    public boolean isUpdated(UUID uuid, String name) {
        return nameUuidFetcher.isUpdated(uuid, name);
    }

    public void insert(UUID uuid, String name) {
        nameUuidFetcher.insert(uuid, name);
    }

    public void update(UUID uuid, String name) {
        nameUuidFetcher.update(uuid, name);
    }
}
