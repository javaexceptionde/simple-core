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
import gnu.trove.impl.sync.TSynchronizedShortByteMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                        if (inventory.getItem(i) != null && inventory.getItem(i).getType().equals(Material.STICK)){
                            System.out.println(i + " Stick");
                        }
                        System.out.println(i + " ");
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
                if (inventory.getItem(i) != null && inventory.getItem(i).getType().equals(Material.STICK)){
                    System.out.println(i + " Stick");
                }
                System.out.println(i + " ");
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
            System.out.println("not exists inv ");
            MYSQL.openConnection(callBack -> {
               try(Connection connection = callBack) {
                   PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player_based_inventory(UUID, IDENTIFIER, CONTENT) VALUES (?, ?, ?)");
                   preparedStatement.setString(1, player.getUniqueId().toString());
                   preparedStatement.setString(2, identifier);
                   preparedStatement.setString(3, inventoryToString(inventory));
                   preparedStatement.executeUpdate();

               }catch (SQLException exception){
                    exception.printStackTrace();
               }
               return false;
            });
            return;
        }
        System.out.println("update called");
        MYSQL.openConnection(callBack -> {
            System.out.println("update....");
           try(Connection connection = callBack) {
               PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player_based_inventory SET CONTENT=? WHERE UUID=? AND IDENTIFIER=?;");
               preparedStatement.setString(1, inventoryToString(inventory));
               preparedStatement.setString(2, player.getUniqueId().toString());
               preparedStatement.setString(3, identifier);


               preparedStatement.executeUpdate();
               System.out.println("update.");
           }catch (SQLException exception){
                exception.printStackTrace();
           }
            return false;
        });
    }

    @Override
    public Inventory loadPlayerBasedInventory(Inventory inventory, Player player, String identifier) {
        String invContent = MYSQL.openConnection(connection -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player_based_inventory WHERE UUID=? AND IDENTIFIER=?");
                preparedStatement.setString(1,player.getUniqueId().toString());
                preparedStatement.setString(2, identifier);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next() || String.valueOf(resultSet.getString("CONTENT")) == null) ;
                return String.valueOf(resultSet.getString("CONTENT"));
            }catch (SQLException exception){
                exception.printStackTrace();
                return null;
            }
        });
        System.out.println(invContent);
        return stringToInventory(invContent);
    }

    @Override
    public boolean playerBasedInventoryExists(Player player, String identifier) {
        return MYSQL.openConnection(connection -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player_based_inventory WHERE UUID=? AND IDENTIFIER=?;");
                preparedStatement.setObject(1,player.getUniqueId().toString());
                preparedStatement.setString(2, identifier);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    System.out.println("true");
                    return true;
                }
            }catch (SQLException exception){
                exception.printStackTrace();
                return false;
            }
            return false;
        });
    }

    @Override
    public void saveInventory(Inventory inventory, String identifier) {
        MYSQL.openConnectionAsync(callBack -> {
           try(Connection connection = callBack) {
               PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO inventory_save(content, identifier) VALUES (?, ?)");
               preparedStatement.setString(1, inventoryToString(inventory));
               System.out.println(inventoryToString(inventory));
               preparedStatement.setString(2, identifier);
               preparedStatement.executeUpdate();
           }catch (SQLException exception){
                exception.printStackTrace();
           }
        });
    }

    @Override
    public Inventory loadInventory(String identifier) {
        return stringToInventory(MYSQL.openConnection(connection -> {
           try {
               PreparedStatement preparedStatement = connection.prepareStatement("SELECT content FROM inventory_save WHERE identifier=?");
               preparedStatement.setString(1, identifier);
               ResultSet resultSet = preparedStatement.executeQuery();
               return resultSet.getString(1);
           }catch (SQLException exception){

           }
            return null;
        }));
    }
}


