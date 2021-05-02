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

package dev.jbull.simplecore.listener.bungeecord;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.player.nameUuid.NameUuidFetcher;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    private NameUuidFetcher nameUuidFetcher = Core.getInstance().getNameUuidFetcher();

    @EventHandler
    public void onJoin(LoginEvent event){
        UUID uuid = event.getConnection().getUniqueId();
        if (!nameUuidFetcher.hasPlayedOnNetwork(uuid)){
            nameUuidFetcher.insert(uuid, event.getConnection().getName());
        }else {
            if (!nameUuidFetcher.getName(uuid).equalsIgnoreCase(event.getConnection().getName())){
                nameUuidFetcher.update(uuid, event.getConnection().getName());
            }
        }
    }
}
