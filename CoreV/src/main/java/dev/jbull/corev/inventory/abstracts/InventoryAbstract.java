package dev.jbull.corev.inventory.abstracts;

import dev.jbull.corev.inventory.interfaces.IInventory;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class InventoryAbstract implements IInventory {
    private String tittle;
    private int size;
    private ConcurrentHashMap<Integer, ItemStack> slots = new ConcurrentHashMap<Integer, ItemStack>();

    public InventoryAbstract(String tittle, int size){
        this.tittle = tittle;
        this.size = size;
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        slots.put(slot, itemStack);
    }

    @Override
    public ItemStack getItem(int size) {
        return slots.get(size);
    }

    @Override
    public void open(Player player) {

    }

    @Override
    public void close(Player player) {

    }
}
