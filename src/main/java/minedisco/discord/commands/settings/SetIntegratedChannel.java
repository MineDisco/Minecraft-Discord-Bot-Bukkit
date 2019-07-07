package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;
import net.dv8tion.jda.core.entities.Message;

import minedisco.discord.DiscordBotSettings;

@CommandDescription(name = "integratedchannel", triggers = {"integratedchannel",
        "setintegratedchannel" }, description = "Sets current channel to be integrated with Minecraft.", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1"), 
                @CommandAttribute(key = "canUseOnAnyChannel", value = "1") })
public class SetIntegratedChannel implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        DiscordBotSettings.setDiscordChannelID(message.getChannel().getId());
        message.getChannel().sendMessage("This channel is now integrated with Minecraft chat").queue();
    }

}
