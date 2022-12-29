package net.harmoniamc.hydrogen.listeners;

import net.harmoniamc.hydrogen.player.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodModeListener {
    
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        Player player = (Player) event.getEntity();
        Profile profile = new Profile(player);
        if(profile.getGodMode().get(player) == true) event.setCancelled(true);
        return;
    }
}