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

package dev.jbull.simplecore.listener;

import dev.jbull.simplecore.events.AnvilClickEvent;
import dev.jbull.simplecore.inventory.anvil.AnvilMap;
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
