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

package dev.jbull.simplecore.player;

import dev.jbull.simplecore.database.sql.HikariConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLCorePlayer extends CorePlayer {
    private HikariConnectionProvider mysql;

    SQLCorePlayer(String name, UUID uuid, String language, int coins, int tokens) {
        super(name, uuid, language, coins, tokens);
    }

    @Override
    public void setName(String name) {
        mysql.openConnectionAsync(callBack -> {
            try(Connection connection = callBack){
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET NAME=? WHERE UUID=?");
                preparedStatement.setString(1,name);
                preparedStatement.setObject(2, getUuid());
                preparedStatement.executeUpdate();
            }catch (SQLException exception){

            }
        });
        mysql.update("UPDATE players SET NAME='" + name + "' WHERE UUID='" + getUuid() + "'");
    }

    @Override
    public void setCoins(int coins) {
        mysql.openConnectionAsync(callBack -> {
           try(Connection connection = callBack) {
               PreparedStatement preparedStatement = callBack.prepareStatement("UPDATE players SET COINS=? WHERE UUID=?");
               preparedStatement.setInt(1, coins);
               preparedStatement.setObject(2, getUuid());
               preparedStatement.executeUpdate();
           }catch (SQLException exception){

           }
        });
        mysql.update("UPDATE players SET COINS='" + coins + "' WHERE UUID='" + getUuid() + "'");
    }

    @Override
    public void addCoins(int coins) {
        setCoins(getCoins() + coins);
    }

    @Override
    public int getCoins() {
        return mysql.openConnection(connection -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COINS FROM players WHERE UUID=?");
                preparedStatement.setString(1, getUuid().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.getInt(1);
            }catch (SQLException exception){
                return null;
            }
        });
    }

    @Override
    public void removeCoins(int coins) {
        setCoins(getCoins() - coins);
    }

    @Override
    public int getTokens() {
        return mysql.openConnection(callBack -> {
            try(Connection connection = callBack) {
                PreparedStatement preparedStatement = callBack.prepareStatement("SELECT TOKENS FROM players WHERE UUID=?");
                preparedStatement.setString(1, getUuid().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.getInt(1);
            }catch (SQLException exception){
            }
            return null;
        });
    }

    @Override
    public void setTokens(int tokens) {
        mysql.openConnectionAsync(callBack -> {
           try(Connection connection = callBack) {
               PreparedStatement preparedStatement = callBack.prepareStatement("UPDATE players SET TOKENS=? WHERE UUID=?");
               preparedStatement.setInt(1, tokens);
               preparedStatement.setObject(2, getUuid());
               preparedStatement.executeUpdate();
           }catch (SQLException exception){

           }
        });
    }

    @Override
    public void addTokens(int tokens) {
        setTokens(getTokens() + tokens);
    }

    @Override
    public void removeTokens(int tokens) {
        setTokens(getTokens() - tokens);
    }
}
