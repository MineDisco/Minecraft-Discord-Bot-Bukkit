package minedisco.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import minedisco.MineDisco;
import minedisco.discord.DiscordBot;

/**
 * 
 */
public class ServerCommandListener implements Listener {

    private DiscordBot bot;

    /**
     * 
     * @param bot
     */
    public ServerCommandListener(DiscordBot bot) {
        this.bot = bot;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerChat(ServerCommandEvent event) {
        if (event.getCommand().toLowerCase().startsWith("say") && event.getCommand().length() >= 4 && !event.getCommand().substring(4).toLowerCase().startsWith(MineDisco
                .getPlugin(MineDisco.class).getConfig().getString("integration.serverSayMessageFilterPrefix"))) {
            bot.sendMessageToChannel("<Server> " + event.getCommand().substring(4));
        }

    }

}