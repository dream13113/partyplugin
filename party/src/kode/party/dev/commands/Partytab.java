package kode.party.dev.commands;

import kode.party.dev.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Partytab implements TabCompleter {

    private Main plugin;

    public Partytab(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("party").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1){
            List<String> on = new ArrayList<>();
            on.add("help");
            on.add("invite");
            on.add("agree");
            on.add("disagree");
            on.add("leave");
            on.add("list");
            on.add("disband");
            return on;

        }
        if(args.length == 2 && args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("agree") || args[0].equalsIgnoreCase("disagree")){
            List<String> on = new ArrayList<>();
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                on.add(players[i].getName());
            }
            return on;
        }
        return null;

    }
}
