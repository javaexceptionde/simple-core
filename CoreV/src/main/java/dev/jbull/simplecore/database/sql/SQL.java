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

package dev.jbull.simplecore.database.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.utils.ExecuteScheduler;
import org.apache.commons.lang3.Validate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class SQL implements ISQL {

    private final HikariConfig config;
    private final HikariDataSource hikari;
    String host;
    String user;
    String pw;
    String database;
    String port;
    private ExecuteScheduler scheduler;

    public SQL(String host, String user, String pw, String databse, String port) {
        this.host = host;
        this.user = user;
        this.pw = pw;
        this.database = databse;
        this.port = port;
        config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://" + this.host + ":" + this.port + "/" + this.database);
        config.setUsername(this.user);
        config.setPassword(this.pw);
        config.setMaximumPoolSize(Runtime.getRuntime().availableProcessors());
        config.setMinimumIdle(3);
        config.setIdleTimeout(Duration.ofMinutes(1).toMillis());
        config.setMaxLifetime(Duration.ofMinutes(10).toMillis());
        config.setConnectionTimeout(30000);
        config.setValidationTimeout(30000);
        hikari = new HikariDataSource(config);
    }

    @Override
    public void disconnect() {
        hikari.close();
    }

    @Override
    public void update(String query) {
        scheduler.schedule(runnable -> {
            try(Connection connection = hikari.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }, 0);
    }

    public CompletableFuture<ResultSet> executeQuery(String query)  {
        return CompletableFuture.supplyAsync(() -> {
            try(Connection connection = hikari.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                return preparedStatement.executeQuery();
            }catch (SQLException exception){

            }
            return null;
        }, scheduler.getExecutor());
    }

    @Override
    public String getString(String database, String select, String conditionType, Object condition) {
        return null;
    }

    @Override
    public String getString(String database, String select, List<String> conditionTypes, List<Object> conditions) {
        Validate.notNull(conditionTypes);
        Validate.notNull(conditions);
        try {
            StringBuilder builder = new StringBuilder("SELECT " + select + " FROM " + database + " WHERE ");
            int i = 0;
            for (String string : conditionTypes){
                if (i == conditionTypes.size() - 1){
                    builder.append(string);
                    builder.append("='");
                    builder.append(conditions.get(i));
                    builder.append("' ");
                }else {
                    builder.append(string);
                    builder.append("='");
                    builder.append(conditions.get(i));
                    builder.append("' ");
                    builder.append(" AND ");
                }
                i++;
            }
            ResultSet resultSet = executeQuery(builder.toString()).get();
            return resultSet.getString(select);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


}
