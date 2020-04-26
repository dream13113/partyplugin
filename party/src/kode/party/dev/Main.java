package kode.party.dev;

import com.mojang.authlib.BaseUserAuthentication;
import kode.party.dev.commands.PartyCmd;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getPluginManager;


public class Main extends JavaPlugin {


    @Override
    public void onEnable() {

        new PartyCmd(this);

    }
}
