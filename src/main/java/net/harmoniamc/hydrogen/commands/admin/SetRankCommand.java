package net.harmoniamc.hydrogen.commands.admin;

import me.empire.flib.command.Command;
import me.empire.flib.command.paramter.Param;
import me.empire.flib.utils.C;
import net.harmoniamc.hydrogen.Hydrogen;
import net.harmoniamc.hydrogen.player.Profile;
import net.harmoniamc.hydrogen.ranks.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SetRankCommand {

    @Command(names = {"setrank", "ogrant", "rank"}, permission = "harmonia.admin", description = "Set the player's rank to the parameter you set")

    public void SetRankCommand(CommandSender player, @Param(name = "target") Player target, @Param(name = "rank") String rank){
        Profile targetProfile = new Profile(target);

        if (!Arrays.stream(Rank.values()).anyMatch((Rank -> Boolean.parseBoolean(rank)))){
            C.sendMessage(player, "&cThat rank does not exists!");
        } else {
            targetProfile.setPlayerRank(target.getUniqueId(), Rank.getByName(rank));
            C.sendMessage(target, Hydrogen.getMessagesFile().getString("SetRank.Other.Rank-Successfully-Set").replaceAll("%rank%", rank.toUpperCase()));
            C.sendMessage(player, Hydrogen.getMessagesFile().getString("SetRank.Sender.Rank-Successfully-Set").replaceAll("%rank%", rank.toUpperCase()).replaceAll("%target%", target.getName()));
        }
    }
}