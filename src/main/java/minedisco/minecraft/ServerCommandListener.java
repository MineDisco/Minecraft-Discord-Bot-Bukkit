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
        String command = event.getCommand();
        if (command != null && command.toLowerCase().startsWith("say") && command.length() >= 4 && !command.substring(4).toLowerCase().startsWith(MineDisco
                .getPlugin(MineDisco.class).getConfig().getString("integration.serverSayMessageFilterPrefix"))) {
            String commandSubSting = command.substring(4);
            if (commandSubSting != null) {
                bot.sendMessageToChannel("<Server> ",  commandSubSting);
            }
            
        }

    }

}