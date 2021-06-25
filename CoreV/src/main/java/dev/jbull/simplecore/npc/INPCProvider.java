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

package dev.jbull.simplecore.npc;

import org.bukkit.Location;

import java.util.ArrayList;

public interface INPCProvider {

    /**
     * Spawns an NPC for all Players online on the Spigot Server and for all future players
     * @param texture The value of the Texture. You can get it from the Mojang API
     * @param signature The Signature of the Texture. You can get it from the Mojang API
     * @param location The Location where the NPC should be spawned
     * @param name The Display Name of the NPC
     * @param id The ID of the NPC it must be unique
     */
    void spawnNPC(String texture, String signature, Location location, String name, String id);

    void destroy(String id);
    ArrayList<NPC> getNpcs();
}
