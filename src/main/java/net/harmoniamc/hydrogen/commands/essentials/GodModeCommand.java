package net.harmoniamc.hydrogen.commands.essentials;

import me.empire.flib.command.Command;
import me.empire.flib.command.paramter.Param;
import me.empire.flib.utils.C;
import net.harmoniamc.hydrogen.Hydrogen;
import net.harmoniamc.hydrogen.player.Profile;
import org.bukkit.entity.Player;

public class GodModeCommand {

    @Command(names = {"godmode", "god", "imgod", "iamgod"}, permission = "hydrogen.godmode", usage = "&eUsage: &d/godmode &r[player]")
    public void GodModeCommand(Player player, @Param(name = "target", required = false) Player target ){
        if(target == null) {
            Profile profile = new Profile(player);
            profile.setGodMode(player, !profile.getGodMode().get(player)); //gl with the messages file
            C.sendMessage(player, profile.getGodMode().get(player) ? Hydrogen.getMessagesFile().getString("GodMode.Sender.Enable") : Hydrogen.getMessagesFile().getString("GodMode.Sender.Disable"));
        } else {
            Profile profile = new Profile(target);
            profile.setGodMode(player, !profile.getGodMode().get(target));
            C.sendMessage(player, profile.getGodMode().get(target) ? Hydrogen.getMessagesFile().getString("GodMode.Other.Sender-Message-Enabled") : Hydrogen.getMessagesFile().getString("GodMode.Other.Sender-Message-Disabled"));
            C.sendMessage(target, profile.getGodMode().get(target) ? Hydrogen.getMessagesFile().getString("GodMode.Other.God-Mode-Enabled") : Hydrogen.getMessagesFile().getString("GodMode.Other.God-Mode-Disabled"));
        }
    }
}