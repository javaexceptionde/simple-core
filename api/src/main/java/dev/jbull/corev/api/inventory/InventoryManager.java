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

package dev.jbull.corev.api.inventory;

import dev.jbull.corev.Core;
import dev.jbull.corev.inventory.anvil.AnvilInventory;

public class InventoryManager {

    public AnvilInventory createInventory(String title, InventoryType inventoryType){
        if (inventoryType.equals(InventoryType.ANVIL)){
            return new AnvilInventory(title, 3);
        }
        return null;
    }
}
