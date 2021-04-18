package dev.jbull.corev.inventory.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IInventory {

    void setItem(int slot, ItemStack itemStack);

    ItemStack getItem(int size);

    void open(Player player);

    void close(Player player);
}
