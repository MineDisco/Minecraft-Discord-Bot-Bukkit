package minedisco.discord;

import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import com.github.rainestormee.jdacommand.CommandHandler;

import minedisco.discord.commands.Set;
import minedisco.discord.handler.MessageHandler;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 *
 */
public class DiscordBot {

  private static final CommandHandler<Message> COMMANDHANDLER = new CommandHandler<Message>();
  public static final DiscordBotSettings BOTSETTINGS = new DiscordBotSettings();
  private JDA jda;
  private Logger logger;

  /**
   *
   * @param token
   * @param logger
   */
  public DiscordBot(String token, Logger logger) {
    try {
      this.logger = logger;
      COMMANDHANDLER.registerCommand(new Set());
      this.jda = new JDABuilder(token).addEventListener(new MessageHandler(COMMANDHANDLER)).build();
      this.jda.awaitReady();
      sendMessageToChannel("Server online");
    } catch (LoginException e) {
      this.logger.severe("Logging to the Discord was not successful. Please check, is the token valid.");
    } catch (InterruptedException e) {
      this.logger.severe("Error waiting JDA to load.");
    }
  }

  /**
   *
   * @param message
   */
  public void sendMessageToChannel(String message) {
    if (DiscordBotSettings.discordChannelIsSet()) {
      TextChannel textChannel = this.jda.getTextChannelById(DiscordBotSettings.getDiscordChannelID());
      if (textChannel != null) {
        textChannel.sendMessage(message).queue();
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

  public void shutConnection() {
    sendMessageToChannelAndWait("Server offline");
    this.jda.shutdownNow();
  }

}
