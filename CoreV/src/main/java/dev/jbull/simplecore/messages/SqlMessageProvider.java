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

package dev.jbull.simplecore.messages;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.database.sql.MySQL;

public class SqlMessageProvider implements IMessageProvider {
    private MySQL mysql = Core.getInstance().getMysql();

    @Override
    public String getMessage(String messageKey, String language){
        return mysql.getString("messages", "Message", "MessageKey", messageKey);
    }

    @Override
    public void updateMessage(String messageKey, String newMessage, String language) {
        mysql.update("UPDATE messages SET Message= '" + newMessage + "' WHERE MessageKey= '" + messageKey + "'");
    }

    @Override
    public boolean messageExists(String messageKey, String language) {
        return mysql.entryExists("messages", "MessageKey", messageKey);
    }

    @Override
    public void createMessage(String messageKey, String message, String language){
        mysql.update("INSERT INTO messages(MessageKey, Message, Language) VALUES ('" + messageKey + "', '" + message + "', '" + language + "')");
    }
}
