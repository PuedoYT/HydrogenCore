package net.harmoniamc.hydrogen.player;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import net.harmoniamc.hydrogen.Hydrogen;
import net.harmoniamc.hydrogen.ranks.Rank;
import net.harmoniamc.hydrogen.utils.MongoUtils;
import net.harmoniamc.hydrogen.utils.Tasks;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.*;

@Getter
@Setter
public class Profile {

    private UUID uuid;
    private String name, lowerCaseName;
    private List<String> permissions;
    @Getter private HashMap<UUID, Rank> playerRank = new HashMap<>();
    @Getter @Setter private HashMap<Player,Boolean> godMode = new HashMap<>();// what class do you want?

    private boolean online, liked, inStaffMode, messagesEnabled, socialSpy;

    private long firstLogin, lastSeen;

    private boolean console = false;


    public Profile(Player player) {
        this(player.getUniqueId(), true);
    }

    public Profile(String name) {
        if (hasProfile(UUID.fromString(name.toLowerCase()))) {
            getProfileFromDb(name.toLowerCase());
        }
    }

    public Profile() {
        this.uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        this.name = "&4Console";
        this.lowerCaseName = "&4console";
        this.playerRank.put(this.uuid, Rank.getDefaultRank());
        this.permissions = Collections.singletonList("*");
        this.online = true;
        this.firstLogin = 0L;
        this.lastSeen = 0L;
        this.console = true;
    }

    public Profile(UUID uuid) {
        this(uuid, true);
    }

    public Profile(Document document) {
        loadFromDocument(document);
    }

    public Profile(UUID uuid, boolean cache) {
        if (hasProfile(uuid)) {
            this.uuid = uuid;
            load(cache);
        } else {
            create(uuid);
        }
    }

    private boolean hasProfile(UUID uuid) {
        Document document = MongoUtils.getProfileCollection().find(Filters.eq("_id", uuid.toString())).first();
        return document != null;
    }

    public void load(boolean cache) {
        getProfileFromDb(uuid);
        if (cache) Hydrogen.getInstance().getProfileHandler().updateProfile(this);
    }

    private void getProfileFromDb(UUID uuid) {
        Document document = MongoUtils.getProfileCollection().find(Filters.eq("_id", uuid.toString())).first();
        assert document != null;
        loadFromDocument(document);
    }

    private void getProfileFromDb(String name) {
        Document document = MongoUtils.getProfileCollection().find(Filters.eq("lname", name.toLowerCase())).first();
        assert document != null;
        loadFromDocument(document);
    }

    public void save() {
        MongoUtils.submitToThread(() -> MongoUtils.getProfileCollection().replaceOne(Filters.eq("_id", uuid.toString()), toBson(), new ReplaceOptions().upsert(true)));
    }

    public void saveOnStop() {
        MongoUtils.getProfileCollection().replaceOne(Filters.eq("_id", uuid.toString()), toBson(), new ReplaceOptions().upsert(true));
    }

    private void create(UUID uuid) {
        this.uuid = uuid;
        if (hasProfile(uuid)) {
            load(true);
            return;
        }
        this.name = "null";
        this.permissions = new ArrayList<>();
        this.firstLogin = System.currentTimeMillis();
        this.lastSeen = System.currentTimeMillis();
        this.online = true;
        this.lowerCaseName = name.toLowerCase();
        Hydrogen.getInstance().getProfileHandler().updateProfile(this);
        save();

        Tasks.runLater(() -> reloadProfile(uuid), 15L);
    }

    private void reloadProfile(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        Document document = MongoUtils.getProfileCollection().find(Filters.eq("_id", uuid)).first();

        this.uuid = uuid;

        if (document != null) {
            this.name = player.getName();
            this.permissions = new ArrayList<>();
            this.firstLogin = document.getLong("firstLogin");
            this.lastSeen = document.getLong("lastSeen");
            this.online = document.getBoolean("online");
            this.lowerCaseName = name.toLowerCase();

            Hydrogen.getInstance().getProfileHandler().updateProfile(this);
            save();
        }
    }

    public Document toBson() {
        Profile profile = this;

        return new Document("_id", profile.getUuid().toString())
                .append("name", profile.getName())
                .append("permissions", profile.getPermissions())
                .append("firstLogin", profile.getFirstLogin())
                .append("lastSeen", profile.getLastSeen())
                .append("online", profile.isOnline())
                .append("lname", profile.getName().toLowerCase());
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    private void loadFromDocument(Document document) {
        this.uuid = UUID.fromString(document.getString("_id"));
        this.name = document.getString("name");
        this.permissions = document.getList("permissions", String.class);
        this.online = document.getBoolean("online");
        this.firstLogin = document.getLong("firstLogin");
        this.lastSeen = document.getLong("lastSeen");
        this.lowerCaseName = name.toLowerCase();

    }

    public void delete() {
        MongoUtils.getProfileCollection().deleteOne(Filters.eq("_id", uuid.toString()));
    }


    public String getName() {
        return name == null ? "CONSOLE" : name;
    }

    public void setPlayerRank(UUID uniqueId, Rank rank) {
        playerRank.put(uniqueId, rank);
    }

    public void setGodMode(Player player, boolean b) {
        getGodMode().put(player, b);
    }
}
