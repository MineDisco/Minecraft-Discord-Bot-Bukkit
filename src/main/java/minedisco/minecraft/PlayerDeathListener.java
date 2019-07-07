package minedisco.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import minedisco.discord.DiscordBot;

public class PlayerDeathListener implements Listener {

    private DiscordBot bot;

    public PlayerDeathListener(DiscordBot bot) {
        this.bot = bot;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerDeath(PlayerDeathEvent event) {
        bot.sendMessageToChannel(event.getDeathMessage());
    }

}
