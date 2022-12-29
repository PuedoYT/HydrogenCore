package net.harmoniamc.hydrogen.commands.essentials;

import me.empire.flib.command.Command;
import me.empire.flib.command.paramter.Param;
import me.empire.flib.utils.C;
import net.harmoniamc.hydrogen.Hydrogen;
import org.bukkit.entity.Player;

public class FlyCommand {

    /* Command parameter */
    @Command(names = {"fly"}, permission = "hydrogen.fly", usage = "&eUsage: &d/fly &r[player]")

    /* Command method, with all arguments*/
    public void FlyCommand(Player player, @Param(name = "target", required = false) Player target){

        /* Checks if the target is null, if it is then allow the sender to fly*/
        if(target == null) {
            player.setAllowFlight(!player.getAllowFlight());
            player.setFlying(player.getAllowFlight());
            C.sendMessage(player, Hydrogen.getMessagesFile().getString(player.isFlying() ? "Fly.Player.Enabled" : "Fly.Player.Disabled"));
        }

        /* Else it checks if the sender has the permission to make others fly */
        else if(player.hasPermission("hydrogen.fly.others"))
        {
            /* Checks if the target is flying */
            if(target.isFlying()){

                target.setAllowFlight(false);
                target.setFlying(false);
                C.sendMessage(target, Hydrogen.getMessagesFile().getString("Fly.Other.Disable"));
                C.sendMessage(player, Hydrogen.getMessagesFile().getString("Fly.Other.Disable-Successful").replace("%target%", target.getDisplayName()));
            }
            else {
                target.setAllowFlight(true);
                target.setFlying(true);
                C.sendMessage(target, Hydrogen.getMessagesFile().getString("Fly.Other.Enable"));
                C.sendMessage(player, Hydrogen.getMessagesFile().getString("Fly.Other.Enable-Successful").replace("%target%", target.getDisplayName()));
            }
        }
    }
}