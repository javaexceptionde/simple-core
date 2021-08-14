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
import dev.jbull.simplecore.utils.Callback;
import dev.jbull.simplecore.utils.ExecuteScheduler;
import dev.jbull.simplecore.utils.IThrowableCallback;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class HikariConnectionProvider {
    private final HikariConfig config;
    private final HikariDataSource hikari;
    String host;
    String user;
    String pw;
    String database;
    String port;
    private ExecuteScheduler scheduler;

    public HikariConnectionProvider(String host, String user, String pw, String databse, String port) {
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

    public boolean isConnected() {
        return !hikari.isClosed();
    }

    public void close() {
        if (isConnected()) {

            hikari.close();

        }
    }

    public Future<Void> openConnectionAsync(Callback<Connection> callback){
        return CompletableFuture.supplyAsync(() -> {
            try(Connection connection = hikari.getConnection()) {
                callback.call(connection);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            return null;
        }, Core.getInstance().getScheduler().getExecutor());
    }

    public <T> T openConnection(IThrowableCallback<Connection, T> callback){
        try(Connection connection = hikari.getConnection()) {
            return callback.call(connection);
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void getResult(final String query, Callback<ResultSet> callback) {

                if (isConnected()) {
                    try(Connection connection = hikari.getConnection()) {
                        callback.call(connection.createStatement().executeQuery(query));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
    }

    public void update(final String query) {

                try(Connection connection = hikari.getConnection()) {
                    connection.createStatement().executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

    }

    public String getString(String database, String selected, String conditionType, String condition) {
        AtomicReference<String> toReturn = new AtomicReference<>();

                try(Connection connection = hikari.getConnection()) {
                    getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "';", (result) -> {
                        try {
                            if (!result.next() || String.valueOf(result.getString(selected)) == null) ;
                            toReturn.set(String.valueOf(result.getString(selected)));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }


        return toReturn.get();
    }

    public String getStringDoubleCondition(String database, String selected, String conditionType, String condition, String conditionType1, String condition1) {
        AtomicReference<String> toReturn = new AtomicReference<>();

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "' && " + conditionType1 + "= '" + condition1 + "';", (result) -> {
                    try {
                        if (!result.next() || String.valueOf(result.getString(selected)) == null) ;
                        toReturn.set(String.valueOf(result.getString(selected)));
                    }catch (SQLException exception){

                    }

                });


        return toReturn.get();
    }

    public Integer getInt(String database, String selected, String conditionType, String condition) {
        AtomicInteger toReturn = new AtomicInteger();

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "';", (result) -> {
                    try {
                        if (!result.next() || Integer.valueOf(result.getInt(selected)) == null) ;
                        toReturn.set(result.getInt(selected));
                    }catch (SQLException exception){

                    }
                });



        return toReturn.get();
    }

    public Integer getIntDoubleCondition(String database, String selected, String conditionType, String condition, String conditionType1, String condition1) {
        AtomicInteger toReturn = new AtomicInteger();

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "' && " + conditionType1 + "= '" + condition1 + "';", (result) -> {
                    try {
                        if (!result.next() || Integer.valueOf(result.getInt(selected)) == null) ;
                        toReturn.set(result.getInt(selected));
                    }catch (SQLException exception){

                    }
                });

        return toReturn.get();
    }

    public Long getLong(String database, String selected, String conditionType, String condition) {
        AtomicLong toReturn = new AtomicLong();

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "';", (result) -> {
                    try {
                        if (!result.next() || Long.valueOf(result.getLong(selected)) == null) ;
                        toReturn.set(result.getLong(selected));
                    }catch (SQLException exception){

                    }
                });

        return toReturn.get();
    }

    public Long getLongDoubleCondition(String database, String selected, String conditionType, String condition, String conditionType1, String condition1) {
        AtomicLong toReturn = new AtomicLong();

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "' && " + conditionType1 + "= '" + condition1 + "';", (result) -> {
                    try {
                        if (!result.next() || Long.valueOf(result.getLong(selected)) == null) ;
                        toReturn.set(result.getLong(selected));
                    }catch (SQLException exception){

                    }

                });

        return toReturn.get();
    }

    public Boolean getBoolean(String database, String selected, String conditionType, String condition) {
        AtomicBoolean toReturn = new AtomicBoolean(false);

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "';", (result) -> {
                    try {
                        if (!result.next() || Boolean.valueOf(result.getBoolean(selected)) == null) ;
                        toReturn.set(result.getBoolean(selected));
                    }catch (SQLException exception){

                    }

                });

        return toReturn.get();
    }

    public Boolean getBooleanDoubleCondition(String database, String selected, String conditionType, String condition, String conditionType1, String condition1) {
        AtomicBoolean toReturn = new AtomicBoolean(false);

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "' && " + conditionType1 + "= '" + condition1 + "';", (result) -> {
                    try {
                        if (!result.next() || Boolean.valueOf(result.getBoolean(selected)) == null) ;
                        toReturn.set(result.getBoolean(selected));
                    }catch (SQLException exception){

                    }

                });

        return toReturn.get();
    }

    public boolean entryExists(String database, String conditiontype, Object condition) {
        AtomicBoolean toReturn = new AtomicBoolean(false);

                getResult("SELECT * FROM " + database + " WHERE " + conditiontype + "= '" + condition + "';", (result) -> {
                    try {
                        if (result.next()) {
                            toReturn.set(result.getString(conditiontype) != null);
                        }
                    }catch (SQLException exception){
                        exception.printStackTrace();
                    }
                });
        System.out.println(toReturn.get());
        return toReturn.get();
    }

    public Boolean entryExistsDoubleCondition(String database, String conditiontype, Object condition, String conditiontype1, Object condition1) {
        AtomicBoolean toReturn = new AtomicBoolean(false);

                getResult("SELECT * FROM " + database + " WHERE " + conditiontype + "= '" + condition + "' && " + conditiontype1 + "= '" + condition1 + "';", (result) -> {
                    try {
                        if (result.next()) {
                            toReturn.set(result.getString(conditiontype) != null);
                        }
                    } catch (SQLException exception) {

                    }
                });
        return toReturn.get();
    }

    public Object getObject(String database, String selected, String conditionType, String condition) {
        AtomicReference toReturn = new AtomicReference();

                getResult("SELECT " + selected + " FROM " + database + " WHERE " + conditionType + "= '" + condition + "';", result -> {
                    try {
                        if (!result.next() || (result.getObject(selected)) == null) ;
                        toReturn.set(result.getObject(selected));
                    }catch (SQLException exception){

                    }
                });
        return toReturn.get();
    }
}
