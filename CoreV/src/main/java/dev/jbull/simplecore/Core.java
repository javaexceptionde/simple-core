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

package dev.jbull.simplecore;

import dev.jbull.simplecore.config.IConfig;
import dev.jbull.simplecore.database.redis.Jedis;
import dev.jbull.simplecore.database.sql.HikariConnectionProvider;
import dev.jbull.simplecore.events.ListenerList;
import dev.jbull.simplecore.inventory.InventoryManager;
import dev.jbull.simplecore.listener.ChannelMessageListener;
import dev.jbull.simplecore.messages.IMessageProvider;
import dev.jbull.simplecore.messages.Language;
import dev.jbull.simplecore.messages.SqlMessageProvider;
import dev.jbull.simplecore.messaging.MessageHandler;
import dev.jbull.simplecore.npc.INPCProvider;
import dev.jbull.simplecore.npc.NPCProvider;
import dev.jbull.simplecore.player.IPlayerManager;
import dev.jbull.simplecore.player.SQLPlayerManager;
import dev.jbull.simplecore.player.nameUuid.NameUuidFetcher;
import dev.jbull.simplecore.utils.ExecuteScheduler;
import io.nats.client.Connection;
import lombok.Getter;

import java.util.logging.Logger;

@Getter
public class Core {
    @Getter
    private static Core instance;
    private ExecuteScheduler scheduler;
    private HikariConnectionProvider mysql;
    private NameUuidFetcher nameUuidFetcher;
    private IConfig yamlConfig;
    private InventoryManager inventoryManager;
    private Jedis jedis;
    private final dev.jbull.simplecore.logger.Logger logger;
    private final boolean debug = false;
    private Connection nc;
    private ListenerList listenerList;
    private IPlayerManager playerManager;
    private MessageHandler messageHandler;
    private IMessageProvider messageProvider;
    private INPCProvider npcProvider;

    public Core(IConfig yamlConfig, Logger logger, MessageHandler handler){
        messageHandler = handler;
        listenerList = new ListenerList();
        listenerList.add(new ChannelMessageListener());
        instance = this;
        this.logger = new dev.jbull.simplecore.logger.Logger(logger);
        scheduler = new ExecuteScheduler();
        this.yamlConfig = yamlConfig;
        this.npcProvider = new NPCProvider();
    }

    public void load(){
        yamlConfig.load(result -> {
            yamlConfig.addDefault("debug", false);
            yamlConfig.addDefault("bungeecord", false);
            yamlConfig.addDefault("database.mysql.host", "127.0.0.1");
            yamlConfig.addDefault("database.mysql.user", "root");
            yamlConfig.addDefault("database.mysql.password", "Test1234");
            yamlConfig.addDefault("database.mysql.database", "Core");
            yamlConfig.addDefault("database.mysql.port", "3306");
            yamlConfig.addDefault("database.redis.host", "127.0.0.1");
            yamlConfig.addDefault("database.redis.port", 6379);
            yamlConfig.addDefault("database.redis.password", "");
            yamlConfig.addDefault("database.redis.use", false);
            yamlConfig.setDefaults();
        });
        if (yamlConfig.getBoolean("database.redis.use")){
            jedis = new Jedis();
        }

        this.mysql = new HikariConnectionProvider(yamlConfig.getString("database.mysql.host"), yamlConfig.getString("database.mysql.user")
                , yamlConfig.getString("database.mysql.password"), yamlConfig.getString("database.mysql.database")
                , yamlConfig.getString("database.mysql.port"));
        mysql.update("CREATE TABLE IF NOT EXISTS nameuuid(UUID VARCHAR(64), NAME TEXT)");
        mysql.update("CREATE TABLE IF NOT EXISTS language(UUID VARCHAR(64), LANGUAGE TEXT)");
        mysql.update("CREATE TABLE IF NOT EXISTS players(UUID VARCHAR(64), NAME VARCHAR(16), LANGUAGE TEXT, COINS INTEGER)");
        mysql.update("CREATE TABLE IF NOT EXISTS player_based_inventory(UUID VARCHAR(64), IDENTIFIER TEXT, CONTENT TEXT)");
        mysql.update("CREATE TABLE IF NOT EXISTS messages(MessageKey TEXT, Message TEXT, Language TEXT)");
        playerManager = new SQLPlayerManager();
        nameUuidFetcher = new NameUuidFetcher();
        inventoryManager = new InventoryManager();
        messageProvider = new SqlMessageProvider();
        messageProvider.createMessage("JOINME_COOLDOWN", "§8[§eServer§8] §7Du musst noch %duration% Minuten warten bis du das näächste Joinme sneden kannst", Language.GERMAN.name());
        messageProvider.createMessage("JOINME_TOKENS", "§8[§eServer§8] §7Du hast zurzeit %tokens% Joinme Tokens", Language.GERMAN.name());
    }

}
