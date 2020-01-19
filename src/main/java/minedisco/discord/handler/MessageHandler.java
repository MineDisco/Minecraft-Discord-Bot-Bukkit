package minedisco.discord.handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.github.rainestormee.jdacommand.AbstractCommand;
import com.github.rainestormee.jdacommand.CommandHandler;

import org.bukkit.ChatColor;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
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
            if (event.getAuthor().isBot()) {
                return;
            }

            if (event.getChannelType().equals(ChannelType.PRIVATE) && !event.getMessage().getContentStripped().isEmpty()
                && MineDisco.getPlugin(MineDisco.class).isLoginListenerEnabled()) {
                 if (event.getMessage().getContentStripped().trim().length() > 3 && event.getMessage().getContentStripped().trim().length() < 6) {
                    String m = MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().authFromDiscord(event.getMessage().getContentStripped(), event.getAuthor().getId());               
                    event.getChannel().sendMessage(m).queue();
                    if (DiscordBotSettings.requestAccessChannelIsSet() && event.getMessage().getContentStripped().trim().length() == 5 ) {
                        event.getJDA().getTextChannelById(DiscordBotSettings.getRequestAccessID())
                        .sendMessage(event.getAuthor().getAsMention() + " is requesting access to the Minecraft server. "+ 
                        event.getJDA().getTextChannelById(DiscordBotSettings.getRequestAccessID()).getAsMention()+
                        " please vote by using reactions to this message.")
                        .queue(msg -> {                           
                            msg.addReaction("U+1F44D").queue(); 
                            msg.addReaction("U+1F44E").queue();
                            MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().setAccessVoteMessageID(event.getAuthor().getId(), msg.getId());
                        });
                    }
                } 
                return;
            }

            if (!event.isFromGuild()) {
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

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        COMMANDEXECUTOR.execute(() -> {
            if (event.getUser().isBot() || !MineDisco.getPlugin(MineDisco.class).isLoginListenerEnabled() || !DiscordBotSettings.requestAccessChannelIsSet()) {
                return;
            }
            if (!event.isFromGuild()) {
                return;
            }
            if (event.getChannel().getId().equals(DiscordBotSettings.getRequestAccessID())) {
                if (event.getReaction().getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1f44d")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(), true, 1);
                } else if (event.getReaction().getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1f44e")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(), false, 1);
                }
               
            }
        });        
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        COMMANDEXECUTOR.execute(() -> {
            if (event.getUser().isBot() || !MineDisco.getPlugin(MineDisco.class).isLoginListenerEnabled() || !DiscordBotSettings.requestAccessChannelIsSet()) {
                return;
            }
            if (!event.isFromGuild()) {
                return;
            }
            if (event.getChannel().getId().equals(DiscordBotSettings.getRequestAccessID())) {
                if (event.getReaction().getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1f44d")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(), true, -1);
                } else if (event.getReaction().getReactionEmote().getAsCodepoints().equalsIgnoreCase("U+1f44e")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(),false, -1);
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
