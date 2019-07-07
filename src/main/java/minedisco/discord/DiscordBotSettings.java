package minedisco.discord;

import minedisco.MineDisco;

/**
 *
 */
public class DiscordBotSettings {
    private static String discordChannelID;
    private static String commandPrefix;
    private static boolean enabledDiscordtoMinecraftChat;

    /**
     *
     */
    public DiscordBotSettings() {
        discordChannelID = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.channelID");
        enabledDiscordtoMinecraftChat = MineDisco.getPlugin(MineDisco.class).getConfig()
                .getBoolean("integration.discordToMinecraftChat");
        commandPrefix = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.commandPrefix");
    }

    /**
     * 
     */
    public static String getCommandPrefix() {
        return commandPrefix;
    }

    /**
     * 
     * @param newCommandPrefix
     */
    public static void setCommandPrefix(String newCommandPrefix) {
        commandPrefix = newCommandPrefix;
        MineDisco.getPlugin(MineDisco.class).getConfig().set("discord.commandPrefix", commandPrefix);
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

    /**
     *
     * @return
     */
    public static boolean isEnabledDiscordtoMinecraftChat() {
        return enabledDiscordtoMinecraftChat && discordChannelIsSet();
    }

    /**
     *
     * @param newEnabledDiscordtoMinecraftChat
     */
    public static void setEnabledDiscordtoMinecraftChat(boolean newEnabledDiscordtoMinecraftChat) {
        enabledDiscordtoMinecraftChat = newEnabledDiscordtoMinecraftChat;
        MineDisco.getPlugin(MineDisco.class).getConfig().set("integration.discordToMinecraftChat",
                enabledDiscordtoMinecraftChat);
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

    /**
     * @return the discordChannelID
     */
    public static String getDiscordChannelID() {
        return discordChannelID;
    }

    /**
     * @param newDiscordChannelID the discordChannelID to set
     */
    public static void setDiscordChannelID(String newDiscordChannelID) {
        discordChannelID = newDiscordChannelID;
        MineDisco.getPlugin(MineDisco.class).getConfig().set("discord.channelID", discordChannelID);
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

    /**
     *
     * @return
     */
    public static boolean discordChannelIsSet() {
        if (discordChannelID.isEmpty() || "DEFAULTCHANNELID".equals(discordChannelID)) {
            return false;
        }
        return true;
    }

}
