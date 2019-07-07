package minedisco.discord.handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandHandler;

import org.bukkit.ChatColor;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import minedisco.MineDisco;
import minedisco.discord.DiscordBotSettings;

public class MessageHandler extends ListenerAdapter {
    private static final ThreadGroup THREADGROUP = new ThreadGroup("Executor");
    private static final Executor COMMANDEXECUTOR = Executors
            .newCachedThreadPool(r -> new Thread(THREADGROUP, r, "Pool"));
    private final CommandHandler<Message> handler;

    public MessageHandler(CommandHandler<Message> handler) {
        this.handler = handler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMessageReceived(MessageReceivedEvent event) {
        COMMANDEXECUTOR.execute(() -> {
            if (event.getAuthor().isBot() || event.getGuild() == null) {
                return;
            }

            if (messageHasPrefix(event.getMessage())) {
                String message = event.getMessage().getContentRaw();
                String[] splitMessage = message.split("\\s+", 2);
                String commandString = splitMessage[0].substring(DiscordBotSettings.getCommandPrefix().length());
                AbstractCommand<Message> command = handler.findCommand(commandString.toLowerCase());
                if (command == null) {
                    return;
                }

                if (!checkCommandExecuteRights(command, event)) {
                    return;
                }

                handler.execute(command, event.getMessage(), splitMessage.length > 1 ? splitMessage[1] : "");
                return;
            }

            if (!DiscordBotSettings.isEnabledDiscordtoMinecraftChat()) {
                return;
            }

            if (event.getChannel().getId().equals(DiscordBotSettings.getDiscordChannelID()) && !event.getMessage().getContentStripped().isEmpty()) {
                if (event.getMessage().isWebhookMessage()) {
                    MineDisco.getPlugin(MineDisco.class).getServer()
                            .broadcastMessage(ChatColor.DARK_AQUA + "<" + event.getAuthor().getName() + "> "
                                    + ChatColor.WHITE + event.getMessage().getContentStripped());
                } else {
                    MineDisco.getPlugin(MineDisco.class).getServer()
                            .broadcastMessage(ChatColor.DARK_AQUA + "<" + event.getMember().getEffectiveName() + "> "
                                    + ChatColor.WHITE + event.getMessage().getContentStripped());

                }
            }
        });
    }

    private boolean checkCommandExecuteRights(AbstractCommand<Message> command, MessageReceivedEvent event) {
        if (command.hasAttribute("OwnerOnly") && !event.getMember().isOwner()) {
            return false;
        }

        return true;
    }

    private boolean messageHasPrefix(Message message) {
        return message.getContentDisplay().toLowerCase().startsWith(DiscordBotSettings.getCommandPrefix());
    }
}
