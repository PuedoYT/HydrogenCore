package net.harmoniamc.hydrogen.utils;

import net.harmoniamc.hydrogen.Hydrogen;
import net.harmoniamc.hydrogen.player.Profile;
import net.harmoniamc.hydrogen.ranks.Rank;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public class PermissionUtils {

    public static Map<UUID, PermissionAttachment> attachments = new HashMap<>();

    public static void updatePermissions(Player player) {
        if (getAttachment(player) != null) player.removeAttachment(getAttachment(player));
        PermissionAttachment attachment = player.addAttachment(Hydrogen.getInstance());
        Profile profile = Hydrogen.getInstance().getProfileHandler().getByUUID(player.getUniqueId());

        for (String permission : profile.getPermissions()) {
            attachment.setPermission((permission.startsWith("-") ? permission.substring(1) : permission), !permission.startsWith("-"));
        }

        attachments.put(player.getUniqueId(), attachment);
        player.recalculatePermissions();
    }

    public static PermissionAttachment getAttachment(Player player) {
        return attachments.getOrDefault(player.getUniqueId(), null);
    }

}
