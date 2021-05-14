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

package dev.jbull.simplecore.logger;


import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.database.mogodb.MongoDatabase;
import dev.jbull.simplecore.messages.IMessageProvider;

import java.util.UUID;

public class MongoMessageProvider implements IMessageProvider {
    MongoDatabase database = null;

    @Override
    public void createMessage(String messageKey, String message, String language) {
    }

    @Override
    public String getMessage(String messageKey, String language) {
        return null;
    }

    @Override
    public void updateMessage(String messageKey, String newMessage, String language) {

    }

    @Override
    public boolean messageExists(String messageKey, String language) {
        return false;
    }

    @Override
    public String getMessage(String messageKey, UUID uuid) {
        return null;
    }
}
