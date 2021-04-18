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

package dev.jbull.corev.config;

import dev.jbull.corev.utils.Callback;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import sun.security.provider.ConfigFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SpigotConfig implements IConfig {
    private YamlConfiguration configuration;
    private File file;
    private boolean load = false;

    public SpigotConfig(File file){
        configuration = new YamlConfiguration();
        this.file = file;
    }
    @Override
    public void load(Callback<IConfig> callback) {
        load = true;

            try {
                configuration.load(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
            callback.call(this);
            load = false;

    }

    @Override
    public void addDefault(String path, Object value) {
        defaults.put(path, value);
    }

    @Override
    public void setDefaults() {
        defaults.forEach((s, o) -> {
            if (!exists(s)){
                set(s, o);
            }
        });
        save(result -> {});
    }

    @Override
    public void save(Callback<IConfig> callback) {
        scheduler.schedule(runnable -> {
            try {
                configuration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void set(String key, Object value) {
        configuration.set(key, value);
    }

    @Override
    public boolean exists(String key) {
        return configuration.contains(key);
    }

    @Override
    public void get(String key, Callback<Object> callback) {
        callback.call(configuration.get(key));
    }

    @Override
    public void getString(String key, Callback<String> callback) {
        callback.call(configuration.getString(key));
    }

    @Override
    public void getInt(String key, Callback<Integer> callback) {
        callback.call(configuration.getInt(key));
    }

    @Override
    public void getBoolean(String key, Callback<Boolean> callback) {
        callback.call(configuration.getBoolean(key));
    }

    @Override
    public void getDouble(String key, Callback<Double> callback) {
        callback.call(configuration.getDouble(key));
    }

    @Override
    public void getKeys(String key, boolean deep, Callback<Set<String>> callback) {
        callback.call(configuration.getConfigurationSection(key).getKeys(deep));
    }

    @Override
    public Object get(String key) {
        return configuration.get(key);
    }

    @Override
    public String getString(String key) {
        return configuration.getString(key);
    }

    @Override
    public Integer getInt(String key) {
        return configuration.getInt(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return configuration.getBoolean(key);
    }

    @Override
    public Double getDouble(String key) {
        return configuration.getDouble(key);
    }

    @Override
    public Set<String> getKeys(String key, boolean deep) {
        return configuration.getConfigurationSection(key).getKeys(deep);
    }

    @Override
    public void awaitReady(Callback<IConfig> callback) {
        scheduler.schedule(result -> {
            if (!load){
                callback.call(this);
            }
        }, 500);
    }
}
