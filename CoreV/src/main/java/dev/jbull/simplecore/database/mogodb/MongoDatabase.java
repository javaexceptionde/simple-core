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

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

//UNSTABLE
public class MongoDatabase implements IMongoDB {
    private com.mongodb.async.client.MongoClient client;
    private com.mongodb.async.client.MongoDatabase database;

    @Override
    public void connect() {
        client = MongoClients.create("mongodb://localhost");
        database = client.getDatabase("Core");
    }

    @Override
    public MongoClient getClient() {
        return client;
    }

    @Override
    public com.mongodb.async.client.MongoDatabase getDatabase() {
        return database;
    }

    @Override
    public MongoCollection<Document> getCollection(String collection) {
        return database.getCollection(collection);
    }

    @Override
    public void createCollection(String collection) {
        getDatabase().createCollection(collection, (result, t) -> {
            t.printStackTrace();
        });
    }

    @Override
    public void insertOne(String collection, Document document) {
        getDatabase().getCollection(collection).insertOne(document, (result, t) -> {
            t.printStackTrace();
        });
    }

    @Override
    public void updateOne(String collection, String key, String value, Document update) {
        getDatabase().getCollection(collection).updateOne(Filters.eq(key, value), update, (result, t) -> {
            t.printStackTrace();
        });
    }

    @Override
    public void updateOne(String collection, Document query, Document update) {
        getDatabase().getCollection(collection).updateOne(Filters.eq(query), update, (result, t) -> {
            t.printStackTrace();
        });
    }

    @Override
    public Document getDocument(String collection, Document query) {
        AtomicReference<Document> toReturn = new AtomicReference<>();
        getCollection(collection).find(Filters.eq(query)).first((result, t) -> {
            t.printStackTrace();
            toReturn.set(result);
        });
        return toReturn.get();
    }

    @Override
    public boolean contains(String collection, String key, Object value) {
        return contains(collection, new Document(key, value));
    }

    @Override
    public boolean contains(String collection, Map<String, Object> values) {
        return contains(collection, new Document(values));
    }

    @Override
    public boolean contains(String collection, Document query) {
        AtomicLong toReturn = new AtomicLong();
        getCollection(collection).count(query, (result, t) -> {
            t.printStackTrace();
            toReturn.set(result);
        });
        return toReturn.get() != 0;
    }

}
