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
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

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
    MongoDatabase getDatabase();

    /**
     * Gets a MongoCollection by specified name
     * @param collection the name of the collection
     * @return instance of the specified {@link MongoCollection}
     */
    MongoCollection<Document> getCollection(String collection);
}
