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

package dev.jbull.simplecore.events;

import dev.jbull.simplecore.inventory.anvil.AnvilContainer;
import dev.jbull.simplecore.inventory.anvil.AnvilMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class AnvilClickEvent extends CustomEvent implements Cancellable {
    private boolean isCanceled = false;
    private int clickedSlot;
    private AnvilContainer anvilContainer;

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public int getClickedSlot() {
        return clickedSlot;
    }

    public void setClickedSlot(int clickedSlot) {
        this.clickedSlot = clickedSlot;
    }

    public AnvilContainer getAnvilContainer() {
        return anvilContainer;
    }

    public void setAnvilContainer(AnvilContainer anvilContainer) {
        this.anvilContainer = anvilContainer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

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
