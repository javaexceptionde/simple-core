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
