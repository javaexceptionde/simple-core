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

package dev.jbull.simplecore.player;

import dev.jbull.simplecore.messages.Language;

import java.util.UUID;

public interface IPlayerManager {

    /**
     * Checks if the player has played on the Network
     * @param uuid The uuid which should be checked
     * @return returns a boolean if the player exists
     */
    boolean playerExists(UUID uuid);

    /**
     * Checks if the Name is currently registered in the Database
     * @param name The name which should be checked
     * @return returns a boolean if the player exists
     */
    boolean playerExists(String name);

    /**
     * Gets the current instance of the CorePlayer by his UUID
     * @param uuid The UUID which specifies the Player
     * @return the instance of {@link CorePlayer}
     */
    CorePlayer getPlayer(UUID uuid);

    /**
     * Gets an UUID by a player name
     * @param name The name where the uuid should be get
     * @return returns the UUID of the Name given
     */
    UUID getUUID(String name);

    /**
     * Gets an Name by a player uuid
     * @param uuid The uuid where the name should be get
     * @return returns the Name of the uuid given
     */
    String getName(UUID uuid);

    /**
     * Updates the Players language
     * @param player The Player which should be updated
     * @param language The language which should be the new
     */
    void updateLanguage(CorePlayer player, Language language);



}
