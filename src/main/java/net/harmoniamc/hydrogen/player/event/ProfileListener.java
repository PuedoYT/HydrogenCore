package net.harmoniamc.hydrogen.player.event;

import net.harmoniamc.hydrogen.Hydrogen;
import net.harmoniamc.hydrogen.player.Profile;
import net.harmoniamc.hydrogen.player.ProfileHandler;
import net.harmoniamc.hydrogen.utils.Tasks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class ProfileListener implements Listener {

    @EventHandler
    public void onPreLogin(PlayerLoginEvent event) {

        ProfileHandler ph = Hydrogen.getInstance().getProfileHandler();

        if (ph.hasProfile(event.getPlayer().getUniqueId())) {
            Profile oldprofile = ph.getByUUID(event.getPlayer().getUniqueId(), false);
            String newName = event.getPlayer().getName();
            if (!newName.equals(oldprofile.getName())) {
                oldprofile.setName(newName);
                oldprofile.setLowerCaseName(newName.toLowerCase());
            }
            oldprofile.save();
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        Hydrogen.getInstance().getProfileHandler().getByUUID(event.getUniqueId());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Hydrogen.getInstance().getExecutor().execute(() -> {
            ProfileHandler ph = Hydrogen.getInstance().getProfileHandler();
            Profile p = ph.getByPlayer(event.getPlayer());
                Tasks.runLater(() -> {
                    p.setOnline(true);
                    p.save();
                }, 10L);
            });
        }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Hydrogen.getInstance().getExecutor().execute(() -> {
            Profile profile = Hydrogen.getInstance().getProfileHandler().getByPlayer(event.getPlayer());
            try {
                profile.setLastSeen(System.currentTimeMillis());
                profile.setOnline(false);
                profile.save();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
        Hydrogen.getInstance().getExecutor().execute(() -> {
            Profile profile = Hydrogen.getInstance().getProfileHandler().getByPlayer(event.getPlayer());
            try {
                profile.setLastSeen(System.currentTimeMillis());
                profile.setOnline(false);
                profile.save();

                Hydrogen.getInstance().getProfileHandler().removeFromCache(event.getPlayer().getUniqueId());
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        });
    }
}
