package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;

import minedisco.MineDisco;
import net.dv8tion.jda.api.entities.Message;

@CommandDescription(name = "deathmessages", triggers = { "deathmessages",
        "death", "deadMessagesToDiscord" }, description = "Sets on/off player death messages in Discord", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetDeathMessagesToDiscord implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (MineDisco.getPlugin(MineDisco.class).getConfig().getBoolean("integration.deathMessagesToDiscord")) {
            MineDisco.getPlugin(MineDisco.class).disablePlayerDeathListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.deathMessagesToDiscord", false);
            message.getChannel().sendMessage("Player death messages disabled").queue();
        } else {
            MineDisco.getPlugin(MineDisco.class).enablePlayerDeathListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.deathMessagesToDiscord", true);        
            message.getChannel().sendMessage("Player death messages enabled").queue();
        }
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

}
