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

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public class NPCProvider implements INPCProvider {
    private final ArrayList<NPC> npcs = new ArrayList<>();

    @Override
    public void spawnNPC(String texture, String signature, Location location, String name, String id) {
        NPC npc = new NPC(location, texture, signature, name, id);
        Bukkit.getOnlinePlayers().forEach(npc::spawn);
        npcs.add(npc);
    }

    @Override
    public void destroy(String id) {

    }

    @Override
    public ArrayList<NPC> getNpcs() {
        return npcs;
    }
}
