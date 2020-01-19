package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;

import minedisco.MineDisco;
import net.dv8tion.jda.api.entities.Message;

@CommandDescription(name = "joinquitmessages", triggers = {
        "join", "quit", "joinQuitMessagesToDiscord" }, description = "Sets on/off join/disconnect messages on Discord.", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetJoinQuitMessagesToDiscord implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (MineDisco.getPlugin(MineDisco.class).getConfig().getBoolean("integration.joinQuitMessagesToDiscord")) {
            MineDisco.getPlugin(MineDisco.class).disablePlayerJoinQuitListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.joinQuitMessagesToDiscord", false);
            message.getChannel().sendMessage("Player death messages disabled").queue();
        } else {
            MineDisco.getPlugin(MineDisco.class).enablePlayerJoinQuitListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.joinQuitMessagesToDiscord", true);        
            message.getChannel().sendMessage("Player death messages enabled").queue();
        }
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

}
