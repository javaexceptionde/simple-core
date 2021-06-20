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

package dev.jbull.simplecore.config;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.logger.Logger;
import dev.jbull.simplecore.utils.Callback;
import dev.jbull.simplecore.utils.ExecuteScheduler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface IConfig {
    ExecuteScheduler scheduler = Core.getInstance().getScheduler();
    ConcurrentHashMap<String, Object> defaults = new ConcurrentHashMap<>();
    Logger logger = Core.getInstance().getLogger();

    /**
     * Loads the configured Config synchronously
     * @param callback calls you the current instance of the IConfig
     */
    public void load(Callback<IConfig> callback);

    /**
     * Adds a default to the Config
     * @param path The path of the default entry
     * @param value The default value of the specified path
     */
    public void addDefault(String path, Object value);

    /**
     * Sets the configured Stuff from the addDefault Method
     * to the Config and saves the Config
     * It only creates the parts and doesn't update the Values
     */
    public void setDefaults();

    /**
     * Saves the Configuration to File asynchronously
     * @param callback calls the current instance of IConfig
     */
    public void save(Callback<IConfig> callback);

    /**
     * Sets a path and value to the Config or updates it when it already exists
     * @param key the Path were the value should stored
     * @param value the Value which would saved to the Path
     */
    public void set(String key, Object value);

    /**
     * Checks if the specified path exists in the Config
     * @param key The path you want to check
     * @return returns a boolean if the specified path exists
     */
    public boolean exists(String key);

    /**
     * Gets an Object of the specified path
     * @param key The path were the value should get from
     * @param callback the callback contains the value of the given key
     */
    public void get(String key, Callback<Object> callback);

    /**
     * Gets an String of the specified path
     * @param key The path were the value should get from
     * @param callback the callback contains the value of the given key
     */
    public void getString(String key, Callback<String> callback);

    /**
     * Gets an Integer of the specified path
     * @param key The path were the value should get from
     * @param callback the callback contains the value of the given key
     */
    public void getInt(String key, Callback<Integer> callback);

    /**
     * Gets an Boolean of the specified path
     * @param key The path were the value should get from
     * @param callback the callback contains the value of the given key
     */
    public void getBoolean(String key, Callback<Boolean> callback);

    /**
     * Gets an Double of the specified path
     * @param key The path were the value should get from
     * @param callback the callback contains the value of the given key
     */
    public void getDouble(String key, Callback<Double> callback);

    /**
     * Gets an Set of the Strings looked for
     * @param key The path were the keys should be from
     * @param deep Defines if it should checked hierarchical
     * @param callback the callback contains the Set of Strings which were specified
     */
    public void getKeys(String key, boolean deep, Callback<Set<String>> callback);

    /**
     * Gets an Object by the specified path
     * @param key The were the value should get from
     * @return The value you selected
     */
    public Object get(String key);

    /**
     * Gets an String by the specified path
     * @param key The were the value should get from
     * @return The value you selected
     */
    public String getString(String key);

    /**
     * Gets an Integer by the specified path
     * @param key The were the value should get from
     * @return The value you selected
     */
    public Integer getInt(String key);

    /**
     * Gets an Boolean by the specified path
     * @param key The were the value should get from
     * @return The value you selected
     */
    public Boolean getBoolean(String key);

    /**
     * Gets an Double by the specified path
     * @param key The were the value should get from
     * @return The value you selected
     */
    public Double getDouble(String key);

    /**
     * Gets an Set of the Strings looked for
     * @param key The path were the keys should be from
     * @param deep Defines if it should checked hierarchical
     * @return the Set of Strings which were specified
     */
    public Set<String> getKeys(String key, boolean deep);

    /**
     * Gets an Set of the Strings looked for
     * @param deep Defines if it should checked hierarchical
     * @return the Set of Strings which were specified
     */
    public Set<String> getKeys(boolean deep);
}
