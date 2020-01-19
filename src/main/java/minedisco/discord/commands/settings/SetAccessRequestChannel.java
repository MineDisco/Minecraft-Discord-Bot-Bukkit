package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;
import net.dv8tion.jda.api.entities.Message;

import minedisco.discord.DiscordBotSettings;

@CommandDescription(name = "AccessRequestChannel", triggers = {"accessrequestchannel",
        "setaccessrequestchannel" }, description = "Sets current channel to be channel where Bot sends Minecraft access votes.", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1"), 
                @CommandAttribute(key = "canUseOnAnyChannel", value = "1") })
public class SetAccessRequestChannel implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        DiscordBotSettings.setRequestAccessChannelID(message.getChannel().getId());
        message.getChannel().sendMessage("This channel is now handle requests to get access to Minecraft Server").queue();
    }

}
