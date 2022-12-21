package minedisco.discord;

import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import minedisco.MineDisco;
import minedisco.discord.handler.MessageHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MarkdownUtil;

/**
 *
 */
public class DiscordBot {

  public static final DiscordBotSettings BOTSETTINGS = new DiscordBotSettings();
  private JDA jda;
  private Logger logger;
  private EmbedBuilder embedBuilder;

  /**
   *
   * @param token
   * @param logger
   */
  public DiscordBot(String token, Logger logger) {
    try {
      this.logger = logger;
      this.jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MESSAGES,GatewayIntent.DIRECT_MESSAGES,GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_EMOJIS_AND_STICKERS).addEventListeners(new MessageHandler()).build();
      this.jda.awaitReady();
    } catch (InterruptedException e) {
      this.logger.severe("Error waiting JDA to load.");
    }
  }

  public void enableStatusChannel() {
    if (DiscordBotSettings.discordStatusChannelIsSet() && !DiscordBotSettings.discordStatusMessageIsSet()) {
      this.createStatusEmbed();
    } else if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      this.setStatusOnline();
    } else {
      this.logger.info("Server status channel has not been set.");
    }
  }

  /**
   *
   * @param message
   */
  public void sendMessageToChannel(@Nonnull String sender, @Nonnull String message) {
    if (DiscordBotSettings.discordChannelIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getDiscordChannelID());
      if (textChannel != null) {
        textChannel.sendMessage(MarkdownUtil.monospace(DiscordBotSettings.getServerName()) + "\n"
            + MarkdownUtil.quoteBlock(MarkdownUtil.bold(sender) + message)).queue();

      } else {
        this.logger.warning("Could not find channel id: " + DiscordBotSettings.getDiscordChannelID());
      }
    } else {
      this.logger.warning("You have not set integrated Discord channel ID.");
    }
  }

  /**
   *
   * @param message
   */
  public void sendMessageToChannelAndWait(@Nonnull String message) {
    if (DiscordBotSettings.discordChannelIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getDiscordChannelID());
      if (textChannel != null) {
        textChannel.sendMessage(message).complete();
      } else {
        this.logger.warning("Could not find channel id: " + DiscordBotSettings.getDiscordChannelID());
      }
    } else {
      this.logger.warning("You have not set integrated Discord channel ID.");
    }
  }

  public boolean addDefaultRoleToUser(@Nonnull String discordID) {
    if (DiscordBotSettings.ChannelRoleIsSet() && DiscordBotSettings.discordChannelIsSet() && discordID != null) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getDiscordChannelID());
      if (textChannel == null) {
        return false;
      }
      Role role = textChannel.getGuild().getRoleById(DiscordBotSettings.getchannelRoleID());
      Member member = textChannel.getGuild().getMemberById(discordID);
      if (role != null && member != null) {
        textChannel.getGuild().addRoleToMember(member.getUser(), role).queue();
        return true;
      }
    } else {
      this.logger.warning("You have not set integrated roles ID");
    }
    return false;
  }

  public boolean removeDefaultRoleToUser(@Nonnull String discordID) {
    if (DiscordBotSettings.ChannelRoleIsSet() && DiscordBotSettings.discordChannelIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getDiscordChannelID());
      if (textChannel != null) {
        Role role = textChannel.getGuild().getRoleById(DiscordBotSettings.getchannelRoleID());
        Member member = textChannel.getGuild().getMemberById(discordID);
        if (role != null && member != null) {
          textChannel.getGuild().removeRoleFromMember(member.getUser(), role).queue();
          return true;
        }
      }
    } else {
      this.logger.warning("You have not set integrated roles ID");
    }
    return false;
  }

  public boolean createStatusEmbed() {
    if (DiscordBotSettings.discordStatusChannelIsSet() && !DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      if (textChannel != null) {
        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.green);
        this.embedBuilder.setTitle(DiscordBotSettings.getServerName() + " 🟢");
        MessageEmbed embedMessage = this.embedBuilder.build();
  
        textChannel.sendMessageEmbeds(embedMessage).queue(msg -> {
          DiscordBotSettings.setStatusMessageID(msg.getId());
        });
      }
    } else {
      this.logger.warning("You have not set status channel id");
    }
    return false;
  }

  public boolean setStatusOnline() {
    if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      if (textChannel != null) {
        textChannel.retrieveMessageById(DiscordBotSettings.getStatusMessageID()).queue(new Consumer<Message>() {
          @Override
          public void accept(Message msg) {
            if (msg.getEmbeds().size() > 0) {
              MessageEmbed em = msg.getEmbeds().get(0);
              textChannel.editMessageEmbedsById(msg.getId(), new EmbedBuilder(em)
                  .setTitle(DiscordBotSettings.getServerName() + " 🟢").setColor(Color.green).clearFields().build())
                  .queue();
            }
          }
        });
      }
    } else {
      this.logger.warning("You have not set status channel id or message");
    }
    return false;
  }

  public boolean setStatusOffline() {
    if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      if (textChannel != null) {
        Message msg = textChannel.retrieveMessageById(DiscordBotSettings.getStatusMessageID()).complete();
        if (msg != null && msg.getEmbeds().size() > 0) {
          MessageEmbed em = msg.getEmbeds().get(0);
          textChannel.editMessageEmbedsById(msg.getId(), new EmbedBuilder(em)
              .setTitle(DiscordBotSettings.getServerName() + " 🔴").setColor(Color.red).clearFields().build()).complete();
      }
        this.jda.shutdownNow();
      }

    } else {
      this.logger.warning("You have not set status channel id or message");
    }
    return false;
  }

  public boolean addPlayer(String playerName) {
    if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      if (textChannel != null) {
        textChannel.retrieveMessageById(DiscordBotSettings.getStatusMessageID()).queue(new Consumer<Message>() {
          @Override
          public void accept(Message msg) {
            if (msg.getEmbeds().size() > 0) {
              MessageEmbed em = msg.getEmbeds().get(0);
              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm");
              LocalTime localTime = LocalTime.now();
              textChannel.editMessageEmbedsById(msg.getId(),
                  new EmbedBuilder(em).addField(new Field(playerName, dtf.format(localTime), true)).build()).queue();
            }
          }
        });
      }
    } else {
      this.logger.warning("You have not set status channel id or message");
    }
    return false;
  }

  public boolean removePlayer(String playerName) {
    if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      if (textChannel != null) {
        textChannel.retrieveMessageById(DiscordBotSettings.getStatusMessageID()).queue(new Consumer<Message>() {
          @Override
          public void accept(Message msg) {
            if (msg.getEmbeds().size() > 0) {
              MessageEmbed em = msg.getEmbeds().get(0);
              EmbedBuilder emb = new EmbedBuilder(em);
              List<Field> fields = emb.getFields();
  
              int i = -1;
              for (Field f : fields) {
                String f2 = f.getName();
                if (f2 != null && f2.equals(playerName)) {
                  i = fields.indexOf(f);
                }
              }
              if (i > -1) {
                emb.getFields().remove(i);
              }
  
              textChannel.editMessageEmbedsById(msg.getId(), emb.build()).queue();
            }
          }
        });
      }

    } else {
      this.logger.warning("You have not set status channel id or message");
    }
    return false;
  }

  public void shutConnection() {

    if (MineDisco.getPlugin(MineDisco.class).getConfig().getBoolean("integration.serverStatusChannel")) {
      this.setStatusOffline();
    }
  }

}
