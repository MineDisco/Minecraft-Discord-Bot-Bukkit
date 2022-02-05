package minedisco;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import minedisco.discord.DiscordBot;
import minedisco.minecraft.*;

/**
 *
 */
public final class MineDisco extends JavaPlugin {
    private DiscordBot bot;
    private PlayerJoinQuitChatListener playerJoinQuitChatListener;
    private PlayerJoinQuitStatusListener playerJoinQuitStatusListener;
    private ChatListener chatListener;
    private PlayerDeathListener playerDeathListener;
    private ServerCommandListener serverCommandListener;
    private PlayerAdvancementListener playerAdvancementListener;
    private PlayerLoginListener playerLoginListener;
    private WhiteListHandler whitelistHandler;


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        String token = this.getConfig().getString("discord.botToken");

        if (token.isEmpty() || "DEFAULTTOKEN".equals(token)) {
            getLogger().severe("Please write Discord Bot token to the config.yml");
        } else {
            this.bot = new DiscordBot(token, getLogger());

            if (this.getConfig().getBoolean("integration.joinQuitMessagesToDiscord")) {
                enablePlayerJoinQuitChatListener();
            }

            if (this.getConfig().getBoolean("integration.serverStatusChannel")) {
                enablePlayerStatusListener();
            }

            if (this.getConfig().getBoolean("integration.mincraftChatToDiscord")) {
                enableChatListener();
            }

            if (this.getConfig().getBoolean("integration.deathMessagesToDiscord")) {
                enablePlayerDeathListener();
            }

            if (this.getConfig().getBoolean("integration.serverSayMessagesToDiscord")) {
                enableServerCommandListener();
            }

            if (this.getConfig().getBoolean("integration.advancementsToDiscord")) {
                enablePlayerAdvancementListener();
            }

            if (this.getConfig().getBoolean("integration.discordWhitelist")) {
                enablePlayerLoginListener();
            }

        }
    }

    @Override
    public void onDisable() {

        if (this.bot != null) {
            bot.shutConnection();
        }
        getLogger().info("MineDisco is disabled");
    }

    public void enablePlayerJoinQuitChatListener() {
        this.playerJoinQuitChatListener = new PlayerJoinQuitChatListener(bot, getServer());
        getServer().getPluginManager().registerEvents(this.playerJoinQuitChatListener, this);
    }

    public void disablePlayerJoinQuitChatListener() {
        HandlerList.unregisterAll(this.playerJoinQuitChatListener);
    }

    public void enablePlayerStatusListener() {
        this.playerJoinQuitStatusListener = new PlayerJoinQuitStatusListener(bot);
        this.bot.enableStatusChannel();
        getServer().getPluginManager().registerEvents(this.playerJoinQuitStatusListener, this);
    }

    public void disablePlayerStatusListener() {
        this.bot.setStatusOffline();
        HandlerList.unregisterAll(this.playerJoinQuitStatusListener);
    }

    public void enableChatListener() {
        this.chatListener = new ChatListener(bot);
        getServer().getPluginManager().registerEvents(this.chatListener, this);
    }

    public void disableChatListener() {
        HandlerList.unregisterAll(this.chatListener);
    }

    public void enablePlayerDeathListener() {
        this.playerDeathListener = new PlayerDeathListener(bot);
        getServer().getPluginManager().registerEvents(this.playerDeathListener, this);
    }

    public void disablePlayerDeathListener() {
        HandlerList.unregisterAll(this.playerDeathListener);
    }

    public void enableServerCommandListener() {
        this.serverCommandListener = new ServerCommandListener(bot);
        getServer().getPluginManager().registerEvents(this.serverCommandListener, this);
    }

    public void disableServerCommandListener() {
        HandlerList.unregisterAll(this.serverCommandListener);
    }

    public void enablePlayerAdvancementListener() {
        this.playerAdvancementListener = new PlayerAdvancementListener(bot);
        getServer().getPluginManager().registerEvents(this.playerAdvancementListener, this);
    }

    public void disablePlayerAdvancementListener() {
        this.whitelistHandler = null;
        HandlerList.unregisterAll(this.playerAdvancementListener);
    }

    public void enablePlayerLoginListener() {
        this.whitelistHandler = new WhiteListHandler(bot, this);
        this.playerLoginListener = new PlayerLoginListener(this.whitelistHandler);
        getServer().getPluginManager().registerEvents(this.playerLoginListener, this);
    }

    public WhiteListHandler getWhiteListHandler() {
        return this.whitelistHandler;
    }

    public boolean isLoginListenerEnabled() {
        return this.whitelistHandler != null;
    }

    public void disablePlayerLoginListener() {
        HandlerList.unregisterAll(this.playerLoginListener);
    }

}
