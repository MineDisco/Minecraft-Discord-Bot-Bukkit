package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;

import minedisco.MineDisco;
import net.dv8tion.jda.api.entities.Message;

@CommandDescription(name = "serverstatus", triggers = {
        "serverstatus", "serverStatusChannelToDiscord" }, description = "Sets on/off server status channel to Discord", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetServerStatusChannelToDiscord implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (MineDisco.getPlugin(MineDisco.class).getConfig().getBoolean("integration.serverStatusChannel")) {
            MineDisco.getPlugin(MineDisco.class).disablePlayerStatusListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.serverStatusChannel", false);
            message.getChannel().sendMessage("Status channel enabled").queue();
        } else {
            MineDisco.getPlugin(MineDisco.class).enablePlayerStatusListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.serverStatusChannel", true);        
            message.getChannel().sendMessage("Status channel disabled.").queue();
        }
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

}
