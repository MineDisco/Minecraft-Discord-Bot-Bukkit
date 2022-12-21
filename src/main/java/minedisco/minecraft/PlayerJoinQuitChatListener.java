package minedisco.minecraft;

import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import minedisco.discord.DiscordBot;

public class PlayerJoinQuitChatListener implements Listener {

    private DiscordBot bot;
    private Server server;

    public PlayerJoinQuitChatListener(DiscordBot bot, Server server) {
        this.bot = bot;
        this.server = server;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        if (playerName != null) {
            bot.sendMessageToChannel(playerName, " logged in. There is now "
                    + this.server.getOnlinePlayers().size() + " players online.");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        if (playerName != null) {
            bot.sendMessageToChannel(playerName, " disconnected. There is now "
                    + (this.server.getOnlinePlayers().size() - 1) + " players online.");
        }
    }

}
