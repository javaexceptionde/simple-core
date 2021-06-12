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

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.database.sql.HikariConnectionProvider;
import dev.jbull.simplecore.messages.Language;
import dev.jbull.simplecore.player.nameUuid.NameUuidFetcher;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class SQLPlayerManager implements IPlayerManager {
    private HikariConnectionProvider mysql = Core.getInstance().getMysql();
    private NameUuidFetcher nameUuidFetcher = Core.getInstance().getNameUuidFetcher();

    @Override
    public boolean playerExists(UUID uuid) {
        return mysql.entryExists("players", "UUID", uuid.toString());
    }

    @Override
    public boolean playerExists(String name) {
        return mysql.entryExists("players", "NAME", name);
    }

    @Override
    public CorePlayer getPlayer(UUID uuid) {
        if (!playerExists(uuid))return null;

        String name = mysql.getString("players", "NAME","UUID", uuid.toString());
        CorePlayer corePlayer = new SQLCorePlayer(name, uuid, Language.GERMAN.toString(),  2, 2);
        return corePlayer;
    }

    @Override
    public UUID getUUID(String name) {
        return nameUuidFetcher.getUUID(name);
    }

    @Override
    public String getName(UUID uuid) {
        return nameUuidFetcher.getName(uuid);
    }

    @Override
    public void updateLanguage(CorePlayer player, Language language) {
        mysql.openConnectionAsync(callBack -> {
            try {
                PreparedStatement preparedStatement = callBack.prepareStatement("UPDATE players SET Language=? WHERE UUID=?");
                preparedStatement.setObject(1, language);
                preparedStatement.setObject(2, player.getUuid());
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }


}
