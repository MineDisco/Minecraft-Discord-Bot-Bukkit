package minedisco.discord.commands.settings;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandAttribute;
import com.github.rainestormee.jdacommand.CommandDescription;

import minedisco.MineDisco;
import net.dv8tion.jda.api.entities.Message;

@CommandDescription(name = "advancementstodiscord", triggers = { "advancementstodiscord",
        "advancements" }, description = "Sets on/off advancement messages integration to Discord", attributes = {
                @CommandAttribute(key = "OwnerOnly", value = "1") })
public class SetAdvancementsToDiscord implements AbstractCommand<Message> {

    @Override
    public void execute(Message message, String args) {
        if (MineDisco.getPlugin(MineDisco.class).getConfig().getBoolean("integration.advancementsToDiscord")) {
            MineDisco.getPlugin(MineDisco.class).disablePlayerAdvancementListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.advancementsToDiscord", false);
            message.getChannel().sendMessage("Advancements integration from Minecraft to Discord disabled").queue();
        } else {
            MineDisco.getPlugin(MineDisco.class).enablePlayerAdvancementListener();
            MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.advancementsToDiscord", true);        
            message.getChannel().sendMessage("Advancements messages integration from Minecraft to Discord enabled").queue();
        }
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

}
