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

import dev.jbull.simplecore.config.BungeeConfig;
import dev.jbull.simplecore.config.IConfig;
import dev.jbull.simplecore.config.SpigotConfig;
import dev.jbull.simplecore.license.License;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

public class CoreBungee extends Plugin {
    private static CoreBungee instance;
    private IConfig licenseConfig;
    private License license;

    @Getter
    private IConfig yamlConfig;

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdir();
        File file = new File(getDataFolder().getPath() +  "/license.yml");
        licenseConfig = new SpigotConfig(file);
        licenseConfig.addDefault("license", "123456789");
        licenseConfig.setDefaults();
        license = new License();
        if (!license.checkLicense(licenseConfig.getString("license"))) {
            getLogger().info("Die Lizenz ist ungültig. ");
            this.onDisable();
        }else {
            File file1 = new File(getDataFolder().getPath() +  "/config.yml");
            yamlConfig = new BungeeConfig(file1);
            registerListener();
        }


    }

    @Override
    public void onDisable() {
            getProxy().getPluginManager().unregisterListeners(this);
            getProxy().getPluginManager().unregisterCommands(this);
    }

    public void registerListener(){

    }

    public static CoreBungee getInstance() {
        return instance;
    }
}
