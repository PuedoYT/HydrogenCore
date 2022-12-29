package net.harmoniamc.hydrogen.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.harmoniamc.hydrogen.Hydrogen;

@Getter
public class MongoDB {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDB() {
        mongoClient = new MongoClient(new MongoClientURI(Hydrogen.getConfigFile().getString("Mongo.URI")));
        database = mongoClient.getDatabase(Hydrogen.getConfigFile().getString("Mongo.DatabaseName"));
    }


}
