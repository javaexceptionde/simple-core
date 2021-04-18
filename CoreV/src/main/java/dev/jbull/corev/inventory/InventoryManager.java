package dev.jbull.corev.inventory;

import dev.jbull.corev.inventory.interfaces.IInventoryManager;
import dev.jbull.corev.utils.Callback;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InventoryManager implements IInventoryManager {

    public void createInventory(String title, int size, Callback<Inventory> callback){
        callback.call(Bukkit.createInventory(null, size, title));
    }
}
