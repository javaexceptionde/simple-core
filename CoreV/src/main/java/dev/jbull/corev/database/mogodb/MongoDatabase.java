package dev.jbull.corev.database.mogodb;

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
