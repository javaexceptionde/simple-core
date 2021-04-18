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

package dev.jbull.corev;

import dev.jbull.corev.config.IConfig;
import dev.jbull.corev.config.SpigotConfig;
import dev.jbull.corev.database.sql.MySQL;
import dev.jbull.corev.inventory.InventoryManager;
import dev.jbull.corev.listener.InventoryClickListener;
import dev.jbull.corev.listener.InventoryCloseListener;
import dev.jbull.corev.listener.PlayerJoinListener;
import dev.jbull.corev.player.nameUuid.NameUuidFetcher;
import dev.jbull.corev.utils.ExecuteScheduler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Core extends JavaPlugin {
    @Getter
    private static Core instance;
    private ExecuteScheduler scheduler;
    private MySQL mysql;
    private NameUuidFetcher nameUuidFetcher;
    private IConfig yamlConfig;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        instance = this;
        scheduler = new ExecuteScheduler();

        getDataFolder().mkdir();
        File file = new File(getDataFolder().getPath() +  "/config.yml");
        yamlConfig = new SpigotConfig(file);
        yamlConfig.load(result -> {
            yamlConfig.addDefault("database.mysql.host", "127.0.0.1");
            yamlConfig.addDefault("database.mysql.user", "root");
            yamlConfig.addDefault("database.mysql.password", "Test1234");
            yamlConfig.addDefault("database.mysql.database", "Core");
            yamlConfig.addDefault("database.mysql.port", "3306");
            yamlConfig.setDefaults();
        });

        initMysql();
        nameUuidFetcher = new NameUuidFetcher();
        inventoryManager = new InventoryManager();
        registerListener();
    }

    @Override
    public void onDisable() {

    }

    public void registerListener(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
    }

    public void initMysql(){

        this.mysql = new MySQL(yamlConfig.getString("database.mysql.host"), yamlConfig.getString("database.mysql.user")
                , yamlConfig.getString("database.mysql.password"), yamlConfig.getString("database.mysql.database")
                , yamlConfig.getString("database.mysql.port"));
        System.out.println("d");
        mysql.update("CREATE TABLE IF NOT EXISTS nameuuid(UUID VARCHAR(64), NAME TEXT)");
    }
}
