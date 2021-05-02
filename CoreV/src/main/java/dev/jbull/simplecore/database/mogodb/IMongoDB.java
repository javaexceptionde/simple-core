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
import com.mongodb.async.client.MongoCollection;
import org.bson.Document;

import java.util.Map;

public interface IMongoDB {

    /**
     * Connects to the Mongo Database
     */
    public void connect();

    /**
     * Gets the MongoClient
     * @return instance of current {@link MongoClient}
     */
    MongoClient getClient();

    /**
     * Gets the Database
     * @return instance of current {@link MongoDatabase}
     */
    com.mongodb.async.client.MongoDatabase getDatabase();

    /**
     * Gets a MongoCollection by specified name
     * @param collection the name of the collection
     * @return instance of the specified {@link MongoCollection}
     */
    MongoCollection<Document> getCollection(String collection);

    /**
     * Creates a MongoCollection.
     * @param collection the Name of the MongoCollection
     */
    void createCollection(String collection);

    /**
     * Insert one Document in a specified Collection
     * @param collection the Collection where the Document should be inserted
     * @param document the Document which should be inserted
     */
    void insertOne(String collection, Document document);

    /**
     * Updates one Document.
     * @param collection The Collection where the Document should be updated.
     * @param key The Key to identify the Document
     * @param value The Value to identify the Document
     * @param update The Values which should be updated
     */
    void updateOne(String collection, String key, String value, Document update);


    void updateOne(String collection, Document query, Document update);

    Document getDocument(String collection, Document query);

    boolean contains(String collection, String key, Object value);

    boolean contains(String collection, Map<String, Object> values);

    boolean contains(String collection, Document query);
}
