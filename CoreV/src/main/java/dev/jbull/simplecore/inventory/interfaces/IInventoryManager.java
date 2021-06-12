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

package dev.jbull.simplecore.inventory.interfaces;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.database.sql.HikariConnectionProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface IInventoryManager {
    HikariConnectionProvider MYSQL = Core.getInstance().getMysql();

    void savePlayerBasedInventory(Inventory inventory, Player player, String identifier);

    Inventory loadPlayerBasedInventory(Inventory inventory, Player player, String identifier);

    boolean playerBasedInventoryExists(Player player, String identifier);

    void saveInventory(Inventory inventory, String identifier);

    Inventory loadInventory(String identifier);
}
