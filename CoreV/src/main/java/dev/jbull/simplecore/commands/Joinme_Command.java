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

package dev.jbull.simplecore.commands;

import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.database.redis.IJedis;
import dev.jbull.simplecore.messages.SqlMessageProvider;
import dev.jbull.simplecore.player.CorePlayer;
import dev.jbull.simplecore.player.ICorePlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Joinme_Command extends Command {
    public Joinme_Command() {
        super("joinme");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (args.length == 0){
            CorePlayer corePlayer = Core.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
            String message = Core.getInstance().getMessageProvider().getMessage("JOINME_HELP", player.getUniqueId());
            Core.getInstance().getMessageHandler().sendMessage("message_to_player", new Document().append("player", player.getUniqueId()).append("message", message));
        }else if (args.length == 1){
            if (args[0].equalsIgnoreCase("send")){
                AtomicBoolean canceled = new AtomicBoolean(false);
                CorePlayer corePlayer = Core.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
                if (corePlayer.getTokens() >= 1){
                    Core.getInstance().getJedis().openConnection(jedis -> {
                        if (!jedis.exists("core.joinme.cooldown." + player.getUniqueId()))return;
                        if (Long.parseLong(jedis.get("core.joinme.cooldown." + player.getUniqueId())) > System.currentTimeMillis()){
                            String message = Core.getInstance().getMessageProvider().getMessage("JOINME_COOLDOWN", player.getUniqueId());
                            Long cooldown = Long.parseLong(jedis.get("core.joinme.cooldown." + player.getUniqueId())) - System.currentTimeMillis();
                            message = message.replace("%duration%", String.valueOf(TimeUnit.MILLISECONDS.toMinutes(cooldown)));
                            Core.getInstance().getMessageHandler().sendMessage("message_to_player", new Document().append("player", player.getUniqueId()).append("message", message));
                            canceled.set(true);
                        }
                    });
                    if (canceled.get())return;
                    corePlayer.removeTokens(1);
                    String message = Core.getInstance().getMessageProvider().getMessage("JOINME_SEND", player.getUniqueId());
                    message = message.replace("%player_name%", player.getName());
                    message = message.replace("%server%", player.getServer().getInfo().getName());
                    BaseComponent[] baseComponents = TextComponent.fromLegacyText(message);
                    baseComponents[0].setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joinme join " + player.getName()));
                    Core.getInstance().getMessageHandler().sendMessage("message_to_all", new Document().append("message", baseComponents));
                    Core.getInstance().getJedis().openConnectionAsync(jedis -> {
                        jedis.set("core.joinme." + player.getUniqueId(), player.getServer().getInfo().getName());
                        jedis.expire("core.joinme." + player.getUniqueId(), 180);
                        jedis.set("core.joinme.cooldown." + player.getUniqueId(), String.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.convert(10, TimeUnit.MILLISECONDS)));
                    });
                }
            }else if (args[0].equalsIgnoreCase("tokens")){
                CorePlayer corePlayer = Core.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
                String message = Core.getInstance().getMessageProvider().getMessage("JOINME_TOKENS", player.getUniqueId());
                message = message.replace("%tokens%", String.valueOf(corePlayer.getTokens()));
                Core.getInstance().getMessageHandler().sendMessage("message_to_player", new Document().append("player", player.getUniqueId()).append("message", message));
            }
        }else if (args.length == 2){
            if (args[0].equalsIgnoreCase("join")){
                UUID uuid = Core.getInstance().getPlayerManager().getUUID(args[1]);
                Core.getInstance().getJedis().openConnection(jedis -> {
                    if (jedis.exists("core.joinme." + uuid)) {
                        CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getPlayerExecutor(uuid).connect(jedis.get("core.joinme." + uuid));
                    }
                });
            }
        }
    }
}
