package dev.jbull.corev.listener;

import dev.jbull.corev.events.AnvilClickEvent;
import dev.jbull.corev.inventory.anvil.AnvilMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent event){
         if (!(event.getWhoClicked() instanceof Player))return;
         Player player = (Player) event.getWhoClicked();
        if (AnvilMap.getInstance().containsKey(player)){
            if (event.getInventory().equals(AnvilMap.getInstance().get(player).getContainer().getBukkitView().getTopInventory())){
                AnvilClickEvent anvilClickEvent = new AnvilClickEvent(player, event.getSlot());
                Bukkit.getPluginManager().callEvent(anvilClickEvent);
                event.setCancelled(anvilClickEvent.isCancelled());
            }
        }
    }
}
