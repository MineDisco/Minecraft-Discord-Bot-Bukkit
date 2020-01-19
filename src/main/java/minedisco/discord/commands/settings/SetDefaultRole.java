package minedisco.discord.commands.settings;

import java.util.List;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import minedisco.discord.DiscordBotSettings;

@CommandDescription(name = "role", triggers = { "channelrole",
        "role" }, description = "Sets role that that have access to synced channel", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetDefaultRole implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (!args.isEmpty() && !DiscordBotSettings.ChannelRoleIsSet()) {
            List<Role> role = message.getGuild().getRolesByName(args, true);
            if (!role.isEmpty()) {
                DiscordBotSettings.setChannelRoleID(role.get(0).getId());
                message.getChannel().sendMessage("Role is now set: " + DiscordBotSettings.getchannelRoleID()).queue();
            } else {
                message.getChannel().sendMessage("Cannot find role.").queue();
            }

        } else {
            message.getChannel().sendMessage("Role is already set. You have to manually change it. (Also, you have to make changes to user's roles)").queue();
        }

    }

}
