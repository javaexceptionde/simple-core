package dev.jbull.corev.inventory;

import dev.jbull.corev.inventory.abstracts.InventoryAbstract;
import dev.jbull.corev.inventory.interfaces.IInventory;
import dev.jbull.corev.inventory.interfaces.IInventoryManager;
import lombok.Data;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory extends InventoryAbstract {

    public Inventory(String tittle, int size) {
        super(tittle, size);
    }

}
