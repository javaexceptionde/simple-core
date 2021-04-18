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

package dev.jbull.simplecore.inventory.abstracts;

import dev.jbull.simplecore.inventory.interfaces.IInventory;
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
