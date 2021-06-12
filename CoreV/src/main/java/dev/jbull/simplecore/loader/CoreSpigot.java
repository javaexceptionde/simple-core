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

package dev.jbull.simplecore.loader;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.commands.NPCCommnad;
import dev.jbull.simplecore.config.IConfig;
import dev.jbull.simplecore.config.SpigotConfig;
import dev.jbull.simplecore.license.License;
import dev.jbull.simplecore.listener.InventoryClickListener;
import dev.jbull.simplecore.listener.InventoryCloseListener;
import dev.jbull.simplecore.listener.PlayerJoinListener;
import dev.jbull.simplecore.messaging.MessageHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class CoreSpigot extends JavaPlugin {
    @Getter
    private static CoreSpigot instance;
    private IConfig yamlConfig;
    private IConfig licenseConfig;
    private License license;

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdirs();
        /*license = new License();
        File file = new File(getDataFolder().getPath() +  "/license.yml");
        licenseConfig = new SpigotConfig(file);
        if (!license.checkLicense(licenseConfig.getString("license"))){
            getLogger().info("License invalid");
            return;
        }*/
        File file1 = new File(getDataFolder().getPath() +  "/config.yml");
        yamlConfig = new SpigotConfig(file1);
        MessageHandler handler = new MessageHandler("localhost", "4222");
        handler.sendMessage("bungeecord", "spigot registry");
        Core core = new Core(yamlConfig, Bukkit.getLogger(), handler);
        core.load();
        registerListener(yamlConfig.getBoolean("bungeecord"));

    }

    @Override
    public void onDisable() {

    }

    public void registerListener(boolean bungeecord){
        if (!bungeecord)Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getCommand("npc").setExecutor(new NPCCommnad());
    }

}
