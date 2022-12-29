package net.harmoniamc.hydrogen.commands.essentials;

import me.empire.flib.command.Command;
import me.empire.flib.command.paramter.Param;
import me.empire.flib.utils.C;
import net.harmoniamc.hydrogen.Hydrogen;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCommand {

    /* Command parameter */
    @Command(names = {"gamemode", "gm", "mode"}, permission = "hydrogen.gamemode")

    /* Command method, with all arguments*/
    public void GamemodeCommand(Player player, @Param(name = "mode")GameMode mode, @Param(name = "target", required = false) Player target){

        /* Checks if the target is null, if it is then sets the sender's gamemode to the 3rd argument*/
        if(target == null)
        {
            player.setGameMode(mode);
            C.sendMessage(player, Hydrogen.getMessagesFile().getString("Gamemode.Successfully-Changed").replaceAll("%mode%", mode.toString().toUpperCase()));
        }

        /* Else it sets the target's gamemode to the chosen gamemode */
        else
        {
            target.setGameMode(mode);
            C.sendMessage(target, Hydrogen.getMessagesFile().getString("Gamemode.Successfully-Changed").replaceAll("%mode%", mode.toString().toUpperCase()));
            C.sendMessage(player, Hydrogen.getMessagesFile().getString("Gamemode.Successfully-Changed-Other").replaceAll("%mode%", mode.toString().toUpperCase()).replaceAll("%target%", target.getName()));
        }

    }
}
