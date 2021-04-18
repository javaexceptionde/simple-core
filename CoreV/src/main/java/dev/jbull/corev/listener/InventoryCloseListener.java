package dev.jbull.corev.listener;

import dev.jbull.corev.inventory.anvil.AnvilMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import javax.print.DocFlavor;

public class InventoryCloseListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event){
        if (!(event.getPlayer() instanceof Player))return;
        Player player = (Player) event.getPlayer();
        if (!AnvilMap.getInstance().containsKey(player))return;
        if (!event.getInventory().equals(AnvilMap.getInstance().get(player).getContainer().getBukkitView().getTopInventory()))return;
        AnvilMap.getInstance().get(player).close(player);
    }
}
