package dev.jbull.corev.events;

import dev.jbull.corev.inventory.Inventory;
import dev.jbull.corev.inventory.anvil.AnvilContainer;
import dev.jbull.corev.inventory.anvil.AnvilMap;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Data
public class AnvilClickEvent extends CustomEvent implements Cancellable {
    private boolean isCanceled = false;
    private int clickedSlot;
    private AnvilContainer anvilContainer;
    private Player player;

    /**
     * Constructs the AnvilClickEvent witch is called by an Click in a custom Anvil
     * @param player the Player who clicked
     * @param clickedSlot the clicked Slot
     */
    public AnvilClickEvent(Player player, int clickedSlot){
        this.clickedSlot = clickedSlot;
        this.player = player;
        this.anvilContainer = AnvilMap.getInstance().get(player).getContainer();
    }

    @Override
    public boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCanceled = b;
    }
}
