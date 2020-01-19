package minedisco.minecraft;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import minedisco.discord.DiscordBot;

public class WhiteListHandler {

    private DiscordBot bot;
    private Server server;
    private Plugin plugin;
    private final String whitelistName = "Minedisco_Whitelist.yml";
    private static HashMap<String, String[]> firstTimeAuthTokens = new HashMap<>();
    private static HashMap<String, String[]> ipAuthTokens = new HashMap<>();
    private FileConfiguration whitelistConfig = null;
    private File whitelistConfigFile = null;

    public WhiteListHandler(DiscordBot bot, Plugin plugin) {
        this.bot = bot;
        this.server = plugin.getServer();
        this.plugin = plugin;
        reloadCustomConfig();
    
    }

    public String startAuthProcess(String playerName, String uuid, String ipAddress) {
        cleanAuthTokens();
        getCustomConfig().set(uuid + ".name", playerName);
        getCustomConfig().set(uuid + ".discordID", "");
        getCustomConfig().set(uuid + ".accessVoteMessageID", "");
        getCustomConfig().set(uuid + ".accessVoteUp", 0);
        getCustomConfig().set(uuid + ".accessVoteDown", 0);
        getCustomConfig().set(uuid + ".authDone", false); 
        getCustomConfig().set(uuid + ".allowedToJoin", false); 
        ArrayList<String> tmpAllowedIP = new ArrayList<String>();
        tmpAllowedIP.add(ipAddress);
        getCustomConfig().set(uuid + ".allowedIPs", tmpAllowedIP);
        int random = new Random().nextInt(100000-9999) + 9999;
        while (firstTimeAuthTokens.containsKey(Integer.toString(random))) {
            random = new Random().nextInt(100000-9999) + 9999;
        }
        firstTimeAuthTokens.put(Integer.toString(random), new String[] {uuid, ipAddress, System.currentTimeMillis() + "" });
        saveCustomConfig();
        return Integer.toString(random);
    }

    public String startIPAuthProcess (String uuid, String ipAddress) {
        cleanAuthTokens();
        int random = new Random().nextInt(10000-999) + 999;
        while (ipAuthTokens.containsKey(Integer.toString(random))) {
            random = new Random().nextInt(10000-999) + 999;
        }
        ipAuthTokens.put(Integer.toString(random), new String[] {uuid, ipAddress, System.currentTimeMillis() + ""});
        return Integer.toString(random);
    }


    public String authFromDiscord(String authToken, String discordID) {
        cleanAuthTokens();
        if (firstTimeAuthTokens.containsKey(authToken)) {
            String uuid = firstTimeAuthTokens.get(authToken)[0];
            getCustomConfig().set(uuid + ".discordID", discordID);
            getCustomConfig().set(uuid + ".authDone", true); 
            getCustomConfig().set(uuid + ".allowedToJoin", true);
            firstTimeAuthTokens.remove(authToken);
            saveCustomConfig();
            return "First time auth done! You can connect to the server when your vote count is positive.";
        }
        if (ipAuthTokens.containsKey(authToken)) {
            String uuid = ipAuthTokens.get(authToken)[0];
            String ip = ipAuthTokens.get(authToken)[1];
            if (getCustomConfig().getString(uuid + ".discordID").equals(discordID)) {
                java.util.List<String> tmpAllowedIps = getCustomConfig().getStringList(uuid + ".allowedIPs");
                tmpAllowedIps.add(ip);
                getCustomConfig().set(uuid + ".allowedIPs", tmpAllowedIps);
                ipAuthTokens.remove(authToken);
                saveCustomConfig();
                return "IP auth done! You can now log in.";
            }
        }
        return  "";
    }

    public void accessVote(String accessVoteMessageID, boolean voteUp, int vote) {
        String owner = getUUIDByAccessVoteMessageID(accessVoteMessageID);
        if (!owner.isEmpty()) {
            if (voteUp) {
                getCustomConfig().set(owner + ".accessVoteUp", getCustomConfig().getInt(owner + ".accessVoteUp") + vote);               
            } else  {
                getCustomConfig().set(owner + ".accessVoteDown", getCustomConfig().getInt(owner + ".accessVoteDown") + vote);
            }
            saveCustomConfig();

            if (checkIfAccessVoteCountPositive(owner)) {
                bot.addDefaultRoleToUser(getCustomConfig().getString(owner +".discordID"));
            } else {
                bot.removeDefaultRoleToUser(getCustomConfig().getString(owner +".discordID"));
            }
        }
        
    }

    private String getUUIDByAccessVoteMessageID(String accessVoteMessageID) {
        for (String var : getCustomConfig().getKeys(false)) {
            if (getCustomConfig().get(var + ".accessVoteMessageID").equals(accessVoteMessageID)) {
                return var;
            }
        }
        return "";
    }

    public void setAccessVoteMessageID(String discordID, String accessVoteMessageID) {
        String owner = getUUIDByDiscordID(discordID);
        if (!owner.isEmpty()) {
            getCustomConfig().set(owner + ".accessVoteMessageID", accessVoteMessageID);
            saveCustomConfig();
        }
    }


    private String getUUIDByDiscordID(String discordID) {
        for (String var : getCustomConfig().getKeys(false)) {
            if (getCustomConfig().get(var + ".discordID") != null && getCustomConfig().get(var + ".discordID").equals(discordID)) {
                return var;
            }
        }
        return "";
    }

    public boolean checkIfAccessVoteCountPositiveByDiscordID(String discordID) {
        String uuid = getUUIDByDiscordID(discordID);
        if (getCustomConfig().getInt(uuid + ".accessVoteUp") - getCustomConfig().getInt(uuid + ".accessVoteDown") >= 1) {
            return true;
        }
        return false;
    }

    public boolean checkIfAccessVoteCountPositive(String uuid) {
        if (getCustomConfig().getInt(uuid + ".accessVoteUp") - getCustomConfig().getInt(uuid + ".accessVoteDown") >= 1) {
            return true;
        }
        return false;
    }

    public boolean checkIfConfirmedIpForUser(String uuid, String ipToFind) {
        return getCustomConfig().getStringList(uuid + ".allowedIPs").stream().anyMatch(ip -> ipToFind.equals(ip));
    }

    public boolean checkIfAllowedToLogin(String uuid) {
        return getCustomConfig().getBoolean(uuid + ".allowedToJoin");
    }

    public boolean checkIfWhitelistContains(String UUID) {
        return whitelistConfig.contains(UUID);
    }

    public boolean checkIfAuthDone(String uuid) {
        return getCustomConfig().getBoolean(uuid + ".authDone");
    }

    public void reloadCustomConfig() {
        if (this.whitelistConfigFile == null) {
            this.whitelistConfigFile = new File(this.plugin.getDataFolder(), this.whitelistName);
        }
        this.whitelistConfig = YamlConfiguration.loadConfiguration(whitelistConfigFile);
    
        InputStream defConfigStream = plugin.getResource(this.whitelistName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            whitelistConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getCustomConfig() {
        if (whitelistConfig == null) {
            reloadCustomConfig();
        }
        return this.whitelistConfig;
    }

    public void saveCustomConfig() {
        if (whitelistConfig == null || whitelistConfigFile == null) {
            return;
        }
        try {
            getCustomConfig().save(whitelistConfigFile);
        } catch (IOException ex) {
            this.server.getLogger().log(Level.SEVERE, "Could not save whitelist to " + whitelistConfigFile, ex);
        }
    }

    private void cleanAuthTokens() {
        if (firstTimeAuthTokens.size() > 0) {
            for (Iterator<Entry<String,String[]>> var = firstTimeAuthTokens.entrySet().iterator(); var.hasNext();) {
                Entry<String,String[]> entry = var.next();
                if (System.currentTimeMillis() - Long.parseLong(entry.getValue()[2]) > 900000 ) {
                    var.remove();
                }
                
            }
        }

        if (ipAuthTokens.size() > 0) {
            for (Iterator<Entry<String,String[]>> var = ipAuthTokens.entrySet().iterator(); var.hasNext();) {
                Entry<String,String[]> entry = var.next();
                if (System.currentTimeMillis() - Long.parseLong(entry.getValue()[2]) > 900000 ) {
                    var.remove();
                }
            }
        }
    }

}