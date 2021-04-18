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

import com.sun.org.apache.xpath.internal.operations.Bool;
import dev.jbull.corev.Core;
import dev.jbull.corev.utils.Callback;
import dev.jbull.corev.utils.ExecuteScheduler;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.io.StringReader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface IConfig {
    ExecuteScheduler scheduler = Core.getInstance().getScheduler();
    ConcurrentHashMap<String, Object> defaults = new ConcurrentHashMap<>();

    public void load(Callback<IConfig> callback);

    public void addDefault(String path, Object value);

    public void setDefaults();

    public void save(Callback<IConfig> callback);

    public void set(String key, Object value);

    public boolean exists(String key);

    public void get(String key, Callback<Object> callback);

    public void getString(String key, Callback<String> callback);

    public void getInt(String key, Callback<Integer> callback);

    public void getBoolean(String key, Callback<Boolean> callback);

    public void getDouble(String key, Callback<Double> callback);

    public void getKeys(String key, boolean deep, Callback<Set<String>> callback);

    public Object get(String key);

    public String getString(String key);

    public Integer getInt(String key);

    public Boolean getBoolean(String key);

    public Double getDouble(String key);

    public Set<String> getKeys(String key, boolean deep);

    public void awaitReady(Callback<IConfig> callback);
}
