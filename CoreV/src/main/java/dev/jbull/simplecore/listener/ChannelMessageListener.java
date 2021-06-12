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

import dev.jbull.simplecore.events.Listener;
import dev.jbull.simplecore.messaging.ChannelMessageEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChannelMessageListener implements Listener {
    @Override
    public void onChannelMessageReceived(ChannelMessageEvent event) {
        if (event.getChannel().equalsIgnoreCase("message_to_player")){
            if (Bukkit.getPlayer(event.getMessage().get("player", UUID.class)) == null)return;
            Bukkit.getPlayer(event.getMessage().get("player", UUID.class)).sendMessage(event.getMessage().getString("message"));
        }else if (event.getChannel().equalsIgnoreCase("message_to_all")){
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.spigot().sendMessage(event.getMessage().get("message", BaseComponent[].class));
            });
        }
    }
}
