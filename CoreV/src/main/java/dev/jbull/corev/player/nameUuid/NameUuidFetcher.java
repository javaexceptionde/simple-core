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

package dev.jbull.corev.player.nameUuid;

import dev.jbull.corev.Core;
import dev.jbull.corev.database.sql.MySQL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.UUID;

public class NameUuidFetcher implements INameUuidFetcher {
    private MySQL mysql = Core.getInstance().getMysql();

    @Override
    public String getName(UUID uuid) {
        return mysql.getString("nameuuid", "NAME", "UUID", uuid.toString());
    }

    @Override
    public UUID getUUID(String name) {
        return UUID.fromString(mysql.getString("nameuuid", "UUID", "NAME", name));
    }

    @Override
    public boolean hasPlayedOnNetwork(UUID uuid) {
        return mysql.entryExists("nameuuid", "UUID", uuid.toString());
    }

    @Override
    public boolean hasPlayedOnNetwork(String name) {
        return mysql.entryExists("nameuuid", "NAME", name);
    }

    @Override
    public boolean isUpdated(UUID uuid, String name) {
        return mysql.entryExistsDoubleCondition("nameuuid", "UUID", uuid.toString(), "NAME", name);
    }

    @Override
    public void insert(UUID uuid, String name) {
        if (hasPlayedOnNetwork(name)){
            UUID otherUUID = getUUID(name);
            update(uuid, getName(uuid));
        }
        mysql.update("INSERT INTO nameuuid(UUID, NAME) VALUES ('" + uuid.toString() + "', '" + name + "')");
    }

    @Override
    public void update(UUID uuid, String name) {
        if (!isUpdated(uuid, name)){
            if (hasPlayedOnNetwork(name)){
                UUID otherUUID = getUUID(name);
                update(otherUUID, getName(otherUUID));
            }
            mysql.update("UPDATE nameuuid SET NAME= '" + name + "' WHERE UUID= '" + uuid.toString() + "'");
        }

    }

    private String getName(String uuid) {

        String output = callURL(
                "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replaceAll("-", ""));

        StringBuilder result = new StringBuilder();

        readName(output, result);

        String name = result.toString();

        return name;
    }

    private String callURL(String URL) {
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            java.net.URL url = new URL(URL);
            urlConn = url.openConnection();

            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);

            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);

                if (bufferedReader != null) {
                    int cp;

                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }

                    bufferedReader.close();
                }
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private void readName(String toRead, StringBuilder result) {
        int i = 49;
        while (i < 200) {
            if (!String.valueOf(toRead.charAt(i)).equalsIgnoreCase("\"")) {
                result.append(String.valueOf(toRead.charAt(i)));

            } else {
                break;
            }
            i++;
        }
    }
}
