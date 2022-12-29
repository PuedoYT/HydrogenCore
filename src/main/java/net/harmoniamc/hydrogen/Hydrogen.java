package net.harmoniamc.hydrogen;

import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.empire.flib.config.FileManager;
import net.harmoniamc.hydrogen.database.MongoDB;
import net.harmoniamc.hydrogen.player.Profile;
import net.harmoniamc.hydrogen.player.ProfileHandler;
import net.harmoniamc.hydrogen.player.event.ProfileListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Copyright @Harmonia /2022\
**/

public class Hydrogen extends JavaPlugin {

    private static Hydrogen instance;
    @Getter private static FileManager configFile;
    @Getter private static FileManager messagesFile;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private Executor dbExecutor, executor;
    @Getter private ProfileHandler profileHandler;

    @Override
    public void onEnable(){
        instance = this;
        registrationHandler();
        fileCreation();
        registerListeners();
    }

    @Override
    public void onDisable(){
        this.profileHandler.cachedProfiles().forEach(Profile::saveOnStop);
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new ProfileListener(), this);
        getServer().getPluginManager().registerEvents(new ProfileListener(), this);
    }

    private void fileCreation(){
        configFile = new FileManager(this, "config.yml");
        messagesFile = new FileManager(this, "messages.yml");
    }

    private void registrationHandler(){
        this.dbExecutor = Executors.newFixedThreadPool(1);
        this.mongoDatabase = new MongoDB().getDatabase();
        this.profileHandler = new ProfileHandler();
        this.executor = ForkJoinPool.commonPool();
    }

    public static Hydrogen getInstance() { return instance; }

}