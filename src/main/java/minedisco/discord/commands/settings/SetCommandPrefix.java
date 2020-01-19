package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;
import net.dv8tion.jda.api.entities.Message;

import minedisco.discord.DiscordBotSettings;

@CommandDescription(name = "commandprefix", triggers = { "commandprefix",
        "prefix" }, description = "Sets prefix for the commands.", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetCommandPrefix implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (!args.isEmpty()) {
            DiscordBotSettings.setCommandPrefix(args);
            message.getChannel().sendMessage("New command prefix is: " + DiscordBotSettings.getCommandPrefix()).queue();
        } else {
            message.getChannel().sendMessage("Prefix cannot be empty. Please pick valid prefix.").queue();
        }

    }

}
