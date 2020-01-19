package minedisco.minecraft;

import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;


import minedisco.discord.DiscordBot;

public class PlayerLoginListener implements Listener {

    private DiscordBot bot;
    private Server server;
    private WhiteListHandler whitelistHandler;

    public PlayerLoginListener(DiscordBot bot, Server server, WhiteListHandler whitelistHandler) {
        this.bot = bot;
        this.server = server;
        this.whitelistHandler = whitelistHandler;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerLogin(PlayerLoginEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();
        if (this.whitelistHandler.checkIfWhitelistContains(uuid) && this.whitelistHandler.checkIfAuthDone(uuid)) {
            if (!this.whitelistHandler.checkIfAllowedToLogin(uuid)) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are not allowed to login. Please contact to the administrator.");                     
            } else if (!this.whitelistHandler.checkIfAccessVoteCountPositive(uuid)) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are not allowed to login. Please wait that your vote count is positive in the access request channel.");                                 
            } else if (!this.whitelistHandler.checkIfConfirmedIpForUser(uuid, event.getRealAddress().getHostAddress())) {
                String authToken = this.whitelistHandler.startIPAuthProcess(uuid, event.getRealAddress().getHostAddress());
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Unknown IP Address for that user, please send following number as private message to the Discord bot:  " + authToken);          
            }
        } else {
            String authToken = this.whitelistHandler.startAuthProcess(event.getPlayer().getName(), uuid, event.getRealAddress().getHostAddress());
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Start auth process, send following number as private message to the Discord Bot: " + authToken);
        }


    }

 

}
