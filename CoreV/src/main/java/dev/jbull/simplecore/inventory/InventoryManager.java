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

package dev.jbull.simplecore.inventory;

import dev.jbull.simplecore.inventory.interfaces.IInventoryManager;
import dev.jbull.simplecore.utils.Callback;
import net.md_5.bungee.forge.IForgeClientPacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class InventoryManager implements IInventoryManager {

    public void createInventory(String title, int size, Callback<Inventory> callback) {
        callback.call(Bukkit.createInventory(null, size, title));
    }

    public String inventoryToString(Inventory inventory) {
        try {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
            data.writeInt(inventory.getSize());
            data.writeObject(inventory.getName());
            for (int i = 0; i < inventory.getSize(); i++) {
                data.writeObject(inventory.getItem(i));
            }
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Inventory stringToInventory(String inventoryData) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(inventoryData));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
            Inventory inventory = Bukkit.createInventory(null, data.readInt(), data.readObject().toString());
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) data.readObject());
            }
            data.close();
            return inventory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void savePlayerBasedInventory(Inventory inventory, Player player, String identifier) {
        if (!playerBasedInventoryExists(player, identifier)){
            mysql.update("INSERT INTO player_based_inventory(UUID, IDENTIFIER, CONTENT) VALUES ('" + player.getUniqueId() + "', '" + identifier + "', '" + inventoryToString(inventory) + "')");
        }
        mysql.update("UPDATE player_based_inventory WHERE UUID= '" + player.getUniqueId() + "' AND IDENTIFIER= '" + identifier + "' SET CONTENT= '" + inventoryToString(inventory) + "'");
    }

    @Override
    public Inventory loadPlayerBasedInventory(Inventory inventory, Player player, String identifier) {
        String invContent = mysql.getStringDoubleCondition("player_based_inventory", "CONTENT", "UUID", player.getUniqueId().toString(), "IDENTIFIER", identifier);
        Inventory inventory1 = stringToInventory(invContent);
        return inventory1;
    }

    @Override
    public boolean playerBasedInventoryExists(Player player, String identifier) {
        return mysql.entryExistsDoubleCondition("player_based_inventory", "UUID", player.getUniqueId().toString(), "IDENTIFIER", identifier);
    }

    @Override
    public void saveInventory(Inventory inventory, String identifier) {
        mysql.update("INSERT INTO inventory_save(content, identifier) VALUES ('" + inventoryToString(inventory) + "', '" + identifier + "')");
    }

    @Override
    public Inventory loadInventory(String identifier) {
        return stringToInventory(mysql.getString("inventory_save", "cotent", "identifier", identifier));
    }
}


