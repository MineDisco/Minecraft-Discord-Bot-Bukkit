package minedisco.discord.handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
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


    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        COMMANDEXECUTOR.execute(() -> {
            if (event.getAuthor().isBot()) {
                return;
            }
            if (event.getChannelType().equals(ChannelType.PRIVATE) && !event.getMessage().getContentStripped().isEmpty()
                && MineDisco.getPlugin(MineDisco.class).isLoginListenerEnabled()) {
                 if (event.getMessage().getContentStripped().trim().length() > 3 && event.getMessage().getContentStripped().trim().length() < 6) {
                    String m = MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().authFromDiscord(event.getMessage().getContentStripped(), event.getAuthor().getId());               
                    if (!m.isEmpty()) {
                        event.getChannel().sendMessage(m).queue();
                    } else  {
                        return;
                    }
                    
                    if (DiscordBotSettings.requestAccessChannelIsSet() && event.getMessage().getContentStripped().trim().length() == 5 ) {
                        TextChannel textChannel = event.getJDA().getTextChannelById(DiscordBotSettings.getRequestAccessID()); 
                        if (textChannel != null) {
                            textChannel.sendMessage(event.getAuthor().getAsMention() + " is requesting access to the Panic Minecraft " + DiscordBotSettings.getServerName() + " server. " + 
                            textChannel.getAsMention() + " please vote by using reactions to this message.")
                            .queue(msg -> {          
                                msg.addReaction(Emoji.fromUnicode("U+1F44D")).queue(); 
                                msg.addReaction(Emoji.fromUnicode("U+1F44E")).queue();
                                MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().setAccessVoteMessageID(event.getAuthor().getId(), msg.getId());
                            });
                        }
                    }
                } 
                return;
            }

            if (!event.isFromGuild()) {
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
                    Member m = event.getMember(); 
                    if (m != null) {
                        MineDisco.getPlugin(MineDisco.class).getServer()
                        .broadcastMessage(ChatColor.DARK_AQUA + "<" + m.getEffectiveName() + "> "
                                + ChatColor.WHITE + event.getMessage().getContentStripped());
                    }
                }
            }
        });
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        COMMANDEXECUTOR.execute(() -> {
            User u = event.getUser();
            if (u != null && u.isBot() || !MineDisco.getPlugin(MineDisco.class).isLoginListenerEnabled() || !DiscordBotSettings.requestAccessChannelIsSet()) {
                return;
            }
            if (!event.isFromGuild()) {
                return;
            }
            if (event.getChannel().getId().equals(DiscordBotSettings.getRequestAccessID())) {
                if (event.getReaction().getEmoji().getType() == Emoji.Type.UNICODE && event.getReaction().getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+1f44d")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(), true, 1);
                } else if (event.getReaction().getEmoji().getType() == Emoji.Type.UNICODE && event.getReaction().getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+1f44e")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(), false, 1);
                }
               
            }
        });        
    }

    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {
        COMMANDEXECUTOR.execute(() -> {
            User u = event.getUser();
            if (u == null || !event.isFromGuild()) {
                return;
            }
            if (u.isBot() || !MineDisco.getPlugin(MineDisco.class).isLoginListenerEnabled() || !DiscordBotSettings.requestAccessChannelIsSet()) {
                return;
            }

            if (event.getChannel().getId().equals(DiscordBotSettings.getRequestAccessID())) {
                if (event.getReaction().getEmoji().getType() == Emoji.Type.UNICODE && event.getReaction().getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+1f44d")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(), true, -1);
                } else if (event.getReaction().getEmoji().getType() == Emoji.Type.UNICODE && event.getReaction().getEmoji().asUnicode().getAsCodepoints().equalsIgnoreCase("U+1f44e")) {
                    MineDisco.getPlugin(MineDisco.class).getWhiteListHandler().accessVote(event.getMessageId(),false, -1);
                }
            }
        });        
    }

}
