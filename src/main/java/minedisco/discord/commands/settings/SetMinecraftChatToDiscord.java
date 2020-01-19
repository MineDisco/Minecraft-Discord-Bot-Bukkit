package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;

import minedisco.MineDisco;
import net.dv8tion.jda.api.entities.Message;

@CommandDescription(name = "minecraftchattodiscord", triggers = {
        "minetodiscord", "minecraftchattodiscord" }, description = "Sets on/off chat integration from Minecraft to Discord.", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetMinecraftChatToDiscord implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (MineDisco.getPlugin(MineDisco.class).getConfig().getBoolean("integration.mincraftChatToDiscord")) {
            MineDisco.getPlugin(MineDisco.class).disableChatListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.mincraftChatToDiscord", false);
            message.getChannel().sendMessage("Chat integration from Minecraft to Discord disabled").queue();
        } else {
            MineDisco.getPlugin(MineDisco.class).enableChatListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.mincraftChatToDiscord", true);        
            message.getChannel().sendMessage("Chat integration from Minecraft to Discord enabled").queue();
        }
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

}
