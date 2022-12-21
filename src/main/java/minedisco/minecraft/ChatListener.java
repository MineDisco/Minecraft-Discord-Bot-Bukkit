package minedisco.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import minedisco.discord.DiscordBot;

/**
 * 
 */
public class ChatListener implements Listener {

    private DiscordBot bot;

    public ChatListener(DiscordBot bot) {
        this.bot = bot;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        if (event != null && msg != null) {
            bot.sendMessageToChannel("<" + event.getPlayer().getDisplayName() + "> ",  msg);
        }
    }

}
