package net.harmoniamc.hydrogen.utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.harmoniamc.hydrogen.Hydrogen;
import org.bson.Document;

public class MongoUtils {

    private static MongoDatabase getDatabase() {
        return Hydrogen.getInstance().getMongoDatabase();
    }

    private static MongoCollection<Document> getCollection(String collection){
        return getDatabase().getCollection(collection);
    }

    public static MongoCollection<Document> getRankCollection(){
        return getCollection("Ranks");
    }

    public static MongoCollection<Document> getProfileCollection(){
        return getCollection("Profiles");
    }

    public static MongoCollection<Document> getPunishmentsCollection() {
        return getCollection("Punishments");
    }


    public static void submitToThread(Runnable runnable) {
        Hydrogen.getInstance().getDbExecutor().execute(runnable);
    }

}
