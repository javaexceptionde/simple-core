package dev.jbull.corev.database.mogodb;

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
