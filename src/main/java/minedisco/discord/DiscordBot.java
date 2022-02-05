package minedisco.discord;

import java.awt.Color;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import com.github.rainestormee.jdacommand.CommandHandler;

import minedisco.MineDisco;
import minedisco.discord.commands.Set;
import minedisco.discord.handler.MessageHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.utils.MarkdownUtil;

/**
 *
 */
public class DiscordBot {

  private static final CommandHandler<Message> COMMANDHANDLER = new CommandHandler<Message>();
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
      COMMANDHANDLER.registerCommand(new Set());
      this.jda = JDABuilder.createDefault(token).addEventListeners(new MessageHandler(COMMANDHANDLER)).build();
      this.jda.awaitReady();

    } catch (LoginException e) {
      this.logger.severe("Logging to the Discord was not successful. Please check, is the token valid.");
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
  public void sendMessageToChannel(String sender, String message) {
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
  public void sendMessageToChannelAndWait(String message) {
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

  public boolean addDefaultRoleToUser(String discordID) {
    if (DiscordBotSettings.ChannelRoleIsSet() && DiscordBotSettings.discordChannelIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getDiscordChannelID());
      Role role = textChannel.getGuild().getRoleById(DiscordBotSettings.getchannelRoleID());
      if (role != null) {
        textChannel.getGuild().addRoleToMember(discordID, role).queue();
        return true;
      }
    } else {
      this.logger.warning("You have not set integrated roles ID");
    }
    return false;
  }

  public boolean removeDefaultRoleToUser(String discordID) {
    if (DiscordBotSettings.ChannelRoleIsSet() && DiscordBotSettings.discordChannelIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getDiscordChannelID());
      Role role = textChannel.getGuild().getRoleById(DiscordBotSettings.getchannelRoleID());
      if (role != null) {
        textChannel.getGuild().removeRoleFromMember(discordID, role).queue();
        return true;
      }
    } else {
      this.logger.warning("You have not set integrated roles ID");
    }
    return false;
  }

  public boolean createStatusEmbed() {
    if (DiscordBotSettings.discordStatusChannelIsSet() && !DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      this.embedBuilder = new EmbedBuilder();
      this.embedBuilder.setColor(Color.green);
      this.embedBuilder.setTitle(DiscordBotSettings.getServerName() + " 🟢");
      MessageEmbed embedMessage = this.embedBuilder.build();

      textChannel.sendMessageEmbeds(embedMessage).queue(msg -> {
        DiscordBotSettings.setStatusMessageID(msg.getId());
      });

    } else {
      this.logger.warning("You have not set status channel id");
    }
    return false;
  }

  public boolean setStatusOnline() {
    if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
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

    } else {
      this.logger.warning("You have not set status channel id or message");
    }
    return false;
  }

  public boolean setStatusOffline() {
    if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      Message msg = textChannel.retrieveMessageById(DiscordBotSettings.getStatusMessageID()).complete();
      if (msg != null && msg.getEmbeds().size() > 0) {
        MessageEmbed em = msg.getEmbeds().get(0);
        textChannel.editMessageEmbedsById(msg.getId(), new EmbedBuilder(em)
            .setTitle(DiscordBotSettings.getServerName() + " 🔴").setColor(Color.red).clearFields().build()).complete();
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

    } else {
      this.logger.warning("You have not set status channel id or message");
    }
    return false;
  }

  public boolean removePlayer(String playerName) {
    if (DiscordBotSettings.discordStatusChannelIsSet() && DiscordBotSettings.discordStatusMessageIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getStatusChannelID());
      textChannel.retrieveMessageById(DiscordBotSettings.getStatusMessageID()).queue(new Consumer<Message>() {
        @Override
        public void accept(Message msg) {
          if (msg.getEmbeds().size() > 0) {
            MessageEmbed em = msg.getEmbeds().get(0);
            EmbedBuilder emb = new EmbedBuilder(em);
            List<Field> fields = emb.getFields();

            int i = -1;
            for (Field f : fields) {
              if (f.getName().equals(playerName)) {
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
