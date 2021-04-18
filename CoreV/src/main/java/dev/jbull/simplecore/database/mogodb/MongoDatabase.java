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

package dev.jbull.simplecore.database.mogodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDatabase implements IMongoDB {
    private MongoClient client;
    private com.mongodb.client.MongoDatabase database;

    @Override
    public void connect() {
        client = new MongoClient("127.0.0.1");
        database = client.getDatabase("");
    }

    @Override
    public MongoClient getClient() {
        return client;
    }

    @Override
    public com.mongodb.client.MongoDatabase getDatabase() {
        return database;
    }

    @Override
    public MongoCollection<Document> getCollection(String collection) {
        return database.getCollection(collection);
    }
}
