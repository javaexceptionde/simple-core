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

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IInventory {

    /**
     * Sets an Item to the specified Slot
     * @param slot the slot where the Item should be set on
     * @param itemStack the ItemStack which defines the Item
     */
    void setItem(int slot, ItemStack itemStack);

    /**
     * Gets an ItemStack by the Slot
     * @param slot the slot where the Item should get form
     * @return The ItemStack of the Slot you selected
     */
    ItemStack getItem(int slot);

    /**
     * Opens the Defined Inventory to a Player
     * @param player The Player where the Inventory should be opened to
     */
    void open(Player player);

    /**
     * Closes the Inventory to a Player
     * @param player The Player where the Inventory should bei closes
     */
    void close(Player player);
}
