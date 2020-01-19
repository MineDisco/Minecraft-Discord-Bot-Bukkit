package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;

import minedisco.MineDisco;
import net.dv8tion.jda.api.entities.Message;

@CommandDescription(name = "serversaymessagestodiscord", triggers = {
        "saytodiscord", "serverSayMessagesToDiscord" }, description = "Sets on/off server say messages integration to Discord", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetServerSayMessagesToDiscord implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (MineDisco.getPlugin(MineDisco.class).getConfig().getBoolean("integration.serverSayMessagesToDiscord")) {
            MineDisco.getPlugin(MineDisco.class).disableServerCommandListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.serverSayMessagesToDiscord", false);
            message.getChannel().sendMessage("Server say messages integration from Minecraft to Discord disabled").queue();
        } else {
            MineDisco.getPlugin(MineDisco.class).enableServerCommandListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.serverSayMessagesToDiscord", true);        
            message.getChannel().sendMessage("Server say messages integration from Minecraft to Discord enabled").queue();
        }
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

}
