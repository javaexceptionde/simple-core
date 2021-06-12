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

import com.zaxxer.hikari.HikariDataSource;
import dev.jbull.simplecore.utils.Callback;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Jedis implements IJedis {
    JedisPool pool;
    JedisPoolConfig jedisPoolConfig;

    public Jedis(){
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(3);
        jedisPoolConfig.setMaxTotal(Runtime.getRuntime().availableProcessors());
        pool = new JedisPool(jedisPoolConfig,config.getString("database.redis.host"), config.getInt("database.redis.port"), 10000, config.getString("database.redis.password"));
    }

    @Override
    public void openConnection(Callback<redis.clients.jedis.Jedis> callback) {
        try(redis.clients.jedis.Jedis jedis = pool.getResource()) {
            callback.call(jedis);
        }catch (Throwable throwable){
            logger.debug("Failed to get Connection from JedisPool. The following error occurred: " + throwable.getMessage());
        }
    }

    @Override
    public void openConnectionAsync(Callback<redis.clients.jedis.Jedis> callback) {
        scheduler.schedule(result -> {
            try(redis.clients.jedis.Jedis jedis = pool.getResource()) {
                callback.call(jedis);
            }catch (Throwable throwable){
                logger.debug("Failed to get Connection from JedisPool. The following error occurred: " + throwable.getMessage());
            }
        }, 0);
    }

    @Override
    public void set(String key, Object value) {
        openConnectionAsync(result -> {
            result.set(key, value.toString());
        });
    }

    @Override
    public void set(String key, Object value, long delete) {
        openConnectionAsync(result -> {
            result.set(key, value.toString());
            result.expire(key, 2);
        });
    }

    @Override
    public String get(String key) {
        AtomicReference<String> toReturn = new AtomicReference<>();
        openConnectionAsync(result -> {
            toReturn.set(result.get(key));
        });
        return toReturn.get();
    }

    @Override
    public void delete(String key) {
        openConnectionAsync(callBack -> {
            callBack.del(key);
        });
    }

    @Override
    public boolean contains(String key) {
        AtomicBoolean toReturn = new AtomicBoolean(false);
        openConnectionAsync(callBack -> {
            toReturn.set(callBack.exists(key));
        });
        return toReturn.get();
    }
}
