package net.harmoniamc.hydrogen.ranks;

import lombok.*;
import me.empire.flib.utils.C;
import org.bukkit.ChatColor;

import java.util.*;

@Getter
@AllArgsConstructor
public enum Rank {

    @Getter OWNER("OWNER", "Owner", C.color("&4Owner"), C.color("&4Owner"), false, ChatColor.DARK_RED, 1000),
    @Getter DEVELOPER("DEVELOPER", "Developer", C.color("&bDeveloper"), C.color("&bDeveloper"), false, ChatColor.AQUA, 900),
    @Getter ADMIN("ADMIN", "Admin", C.color("&4Admin"), C.color("&4Admin"), false, ChatColor.DARK_RED, 800),
    @Getter SRMOD("SRMOD", "SrMod", C.color("&cSrModerator"), C.color("&cSrModerator"), false, ChatColor.RED, 700),
    @Getter MOD("MOD", "Moderator", C.color("&cModerator"), C.color("&cModerator"), false, ChatColor.RED, 600),
    @Getter HELPER("HELPER", "Helper", C.color("&cHelper"), C.color("&cHelper"), false, ChatColor.RED, 500),
    @Getter BUILDER("BUILDER", "Builder", C.color("&dBuilder"), C.color("&dBuilder"), false, ChatColor.LIGHT_PURPLE, 400),
    @Getter PARTNER("PARTNER", "Partner", C.color("&5Partner"), C.color("&5Partner"), false, ChatColor.DARK_PURPLE, 300),
    @Getter MEDIA("MEDIA", "Media", C.color("&9Media"), C.color("&9Media"), false, ChatColor.BLUE, 200),
    @Getter HARMONIAPLUS("HARMONIAPLUS", "Harmonia+", C.color("&6Harmonia+"), C.color("&6Harmonia+"), false, ChatColor.GOLD, 100),
    @Getter VIPPLUS("VIPPLUS", "Vip+", C.color("&aVip+"), C.color("&aVip+"), false, ChatColor.GREEN, 50),
    @Getter VIP("VIP", "Vip", C.color("&aVip"), C.color("&aVip"), false, ChatColor.GREEN, 25),
    @Getter DEFAULT("DEFAULT", "Default", C.color("&7"), C.color("&7Default"), true, ChatColor.GRAY, 1);

    private String ID;
    private String name;
    private String prefix, suffix, displayName;
    private boolean bold, italic, isDefault, isStaff;
    private ChatColor color;

    private int priority;

    private List<String> permissions;

    Rank(String ID, String name, String prefix, String displayName, boolean isDefault, ChatColor color, int priority){
        this.ID = ID;
        this.name = name;
        this.prefix = prefix;
        this.displayName = displayName;
        this.isDefault = isDefault;
        this.color = color;
        this.priority = priority;
    }

    public void addPermission(String perm) {
        this.permissions.add(perm);
    }

    public void removePermission(String perm) {
        this.permissions.remove(perm);
    }

    public static List<Rank> ranks() {
        return new ArrayList<>(List.of(Rank.values()));
    }

    public static Rank getByName(String name) {
        if (ranks().stream().anyMatch(rank -> rank.getName().equalsIgnoreCase(name))) return ranks().stream().filter(rank -> rank.getName().equalsIgnoreCase(name)).findFirst().get();
        else return null;
    }

    public static Rank getDefaultRank() {
        return Rank.DEFAULT;
    }

    public static Rank getHighestRank() {
        return Rank.OWNER;
    }
}
