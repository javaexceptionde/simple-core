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

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.player.nameUuid.NameUuidFetcher;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        NameUuidFetcher nameUuidFetcher = Core.getInstance().getNameUuidFetcher();
        System.out.println(nameUuidFetcher != null);
        System.out.println(Core.getInstance().getMysql() != null);
        Player player = event.getPlayer();
        System.out.println(player != null);
        System.out.println(player.getUniqueId());
        if (!nameUuidFetcher.hasPlayedOnNetwork(player.getUniqueId())){
            nameUuidFetcher.insert(player.getUniqueId(), player.getName());
        }else {
            if (!nameUuidFetcher.getName(player.getUniqueId()).equalsIgnoreCase(player.getName())){
                nameUuidFetcher.update(player.getUniqueId(), player.getName());
            }
        }
    }
}
