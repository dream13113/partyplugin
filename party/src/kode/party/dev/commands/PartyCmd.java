package kode.party.dev.commands;

import kode.party.dev.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PartyCmd implements CommandExecutor, Listener {

    private Main plugin;
    public PartyCmd(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("party").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }




    public HashMap<UUID, UUID> playerparty = new HashMap<UUID, UUID>();
    public HashMap<UUID, ArrayList<UUID>> playersend = new HashMap<UUID, ArrayList<UUID>>();
    public HashMap<UUID, ArrayList<UUID>> partyin = new HashMap<UUID, ArrayList<UUID>>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage( ChatColor.RED  + "이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }


        Player p = (Player) sender;

        if(args.length == 0){
            sender.sendMessage( ChatColor.RED + "파티 명령어 사용법:" + cmd.getUsage());
            return true;
        }

        if(args[0].equalsIgnoreCase("invite")){
            if(playerparty.get(p.getUniqueId()) != p.getUniqueId()){
                p.sendMessage( ChatColor.RED + "파티장이 아닌 플레이어는 멤버를 초대할 수 없습니다.");
                return true;
            }

            if(args.length == 2){
                if(Bukkit.getPlayer(args[1]) != null){
                    InviteParty(Bukkit.getPlayer(args[1]), p);
                    p.sendMessage(ChatColor.YELLOW + "초대장을 발송했습니다.");
                    return true;
                }
                else{
                    sender.sendMessage( ChatColor.RED + "작성하신 이름의 플레이어가 서버에 없습니다.");
                    return true;
                }
            }
            else{
                sender.sendMessage( ChatColor.RED + "파티 명령어 사용법:" + cmd.getUsage());
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("leave")){
            if(playerparty.get(p.getUniqueId()) == p.getUniqueId()){
                p.sendMessage( ChatColor.RED + "파티장은 파티에서 나갈 수 없습니다.");
                return true;
            }
        }
        else if(args[0].equalsIgnoreCase("agree")){
            if(Bukkit.getPlayer(args[1]) == null){
                p.sendMessage( ChatColor.RED + "작성하신 이름의 현재 서버에 없습니다.");
                return true;
            }

            if(args.length == 2){
                for(int i = 0; i < playersend.get(p.getUniqueId()).size(); i++){
                    if(playersend.get(p.getUniqueId()).get(i) == Bukkit.getPlayer(args[1]).getUniqueId()){
                        AgreePlayer(p, Bukkit.getPlayer(args[1]));
                        p.sendMessage( ChatColor.YELLOW + Bukkit.getPlayer(args[1]).getName() + "님의 파티에 가입되었습니다.");
                        return true;
                    }
                }
                p.sendMessage( ChatColor.RED + args[1] + "님의 파티 요청을 받지 않았습니다.");
                return true;
            }else{
                sender.sendMessage( ChatColor.RED + "파티 명령어 사용법:" + cmd.getUsage());
                return true;
            }

        }
        else if(args[0].equalsIgnoreCase("disagree")){
            if(args.length == 2){
                for(int i = 0; i < playersend.get(p.getUniqueId()).size(); i++){
                    if(playersend.get(p.getUniqueId()).get(i) == Bukkit.getPlayer(args[1]).getUniqueId()){
                        DisagreePlayer(p, Bukkit.getPlayer(args[1]));
                        p.sendMessage( ChatColor.YELLOW + Bukkit.getPlayer(args[1]).getName() + "님의 파티 가입 요청을 거부했습니다.");
                        return true;
                    }
                }
                p.sendMessage( ChatColor.RED + args[1] + "님의 파티 요청을 받지 않았습니다.");
            }else{
                sender.sendMessage( ChatColor.RED + "파티 명령어 사용법:" + cmd.getUsage());
                return true;
            }


        }
        else if(args[0].equalsIgnoreCase("promote")){


        }
        else if(args[0].equalsIgnoreCase("disband")){



        }
        else if(args[0].equalsIgnoreCase("list")){
            for(int i = 0; i < partyin.get(playerparty.get(p.getUniqueId())).size(); i++){
                if(Bukkit.getPlayer(partyin.get(playerparty.get(p.getUniqueId())).get(i)) != null ){
                    if(playerparty.get(p.getUniqueId()) == p.getUniqueId()){
                        p.sendMessage(ChatColor.YELLOW + "파티장:" + Bukkit.getPlayer(partyin.get(playerparty.get(p.getUniqueId())).get(i)).getName() + "\n");
                    }
                    else {


                        p.sendMessage(ChatColor.YELLOW + "일반 파티멤버:" + Bukkit.getPlayer(partyin.get(playerparty.get(p.getUniqueId())).get(i)).getName() + "\n");
                    }
                }
                else{
                    if(playerparty.get(p.getUniqueId()) == p.getUniqueId()){
                        p.sendMessage(ChatColor.YELLOW + "파티장:" + Bukkit.getPlayer(partyin.get(playerparty.get(p.getUniqueId())).get(i)).getName() + "\n");
                    }
                    else {


                        p.sendMessage(ChatColor.YELLOW + "일반 파티멤버:" + Bukkit.getPlayer(partyin.get(playerparty.get(p.getUniqueId())).get(i)).getName() + "\n");
                    }

                }
            }
            return true;


        }
        else if(args[0].equalsIgnoreCase("help")){
            sender.sendMessage( ChatColor.RED + "파티 명령어 사용법:" + cmd.getUsage());
            return true;
        }

        return false;
    }



    @EventHandler
    public void playerjoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        if(!p.hasPlayedBefore()){
            playerparty.put(p.getUniqueId(), p.getUniqueId());
        }
        else if(playerparty.get(p.getUniqueId()) == null) {
        }
        playerparty.put(p.getUniqueId(), p.getUniqueId());

        playersend.computeIfAbsent(p.getUniqueId(), k -> new ArrayList<UUID>());
        partyin.computeIfAbsent(p.getUniqueId(), k -> new ArrayList<UUID>());

    }

    public void InviteParty(Player Invitedp, Player Invitingp) {

        Invitedp.sendMessage( Invitingp.getName() + "님의 파티 초대장이 왔습니다.");
        playersend.get(Invitedp.getUniqueId()).add(Invitingp.getUniqueId());
        PlayerConnection connection = ((CraftPlayer) Invitedp).getHandle().playerConnection;
        PacketPlayOutChat packagree = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"§a[수락]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party agree "+ Invitingp.getName() +"\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"§a파티 초대를 수락합니다.\",\"bold\":true}]}}"));
        PacketPlayOutChat packdisagree = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"§c[거절]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party disagree " + Invitingp.getName() + " \"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"§c파티 초대를 거절합니다.\",\"bold\":true}]}}"));
        connection.sendPacket(packagree);
        connection.sendPacket(packdisagree);

    }

    public void AgreePlayer(Player agreep, Player Invitingp){
        playersend.get(agreep.getUniqueId()).remove(Invitingp.getUniqueId());
        playerparty.replace(agreep.getUniqueId(), Invitingp.getUniqueId());
        partyin.get(Invitingp.getUniqueId()).add(agreep.getUniqueId());
    }
    public void DisagreePlayer(Player disagreep, Player Invitingp){
        playersend.get(disagreep.getUniqueId()).remove(Invitingp.getUniqueId());
    }
    public void LeavePlayer(Player leavep){
        partyin.get(playerparty.get(leavep.getUniqueId())).remove(leavep.getUniqueId());
        playerparty.replace(leavep.getUniqueId(), leavep.getUniqueId());
    }


}
