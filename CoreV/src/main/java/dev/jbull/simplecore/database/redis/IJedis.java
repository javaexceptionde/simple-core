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

package dev.jbull.simplecore.database.redis;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.config.IConfig;
import dev.jbull.simplecore.logger.Logger;
import dev.jbull.simplecore.utils.Callback;
import dev.jbull.simplecore.utils.ExecuteScheduler;
import redis.clients.jedis.Jedis;

public interface IJedis {

    ExecuteScheduler scheduler = Core.getInstance().getScheduler();
    IConfig config  = Core.getInstance().getYamlConfig();
    Logger logger = Core.getInstance().getLogger();

    void openConnection(Callback<Jedis> callback);

    void openConnectionAsync(Callback<Jedis> callback);

    void set(String key, Object value);

    void set(String key, Object value, long delete);

    String get(String key);

    void delete(String key);

    boolean contains(String key);
}
