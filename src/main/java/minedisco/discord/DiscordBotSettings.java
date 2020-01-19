package minedisco.discord;

import minedisco.MineDisco;

/**
 *
 */
public class DiscordBotSettings {
    private static String discordChannelID;
    private static String requestAccessChannelID;
    private static String commandPrefix;
    private static boolean enabledDiscordtoMinecraftChat;
    private static String channelRoleID;

    /**
     *
     */
    public DiscordBotSettings() {
        discordChannelID = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.channelID");
        enabledDiscordtoMinecraftChat = MineDisco.getPlugin(MineDisco.class).getConfig()
                .getBoolean("integration.discordToMinecraftChat");
        commandPrefix = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.commandPrefix");
        requestAccessChannelID = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.requestAccessChannelID");
        channelRoleID = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.syncedChannelRoleID");
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

        /**
     * @return the discordChannelID
     */
    public static String getchannelRoleID() {
        return channelRoleID;
    }

    /**
     * @param newDiscordChannelID the discordChannelID to set
     */
    public static void setChannelRoleID(String newChannelRoleID) {
        channelRoleID = newChannelRoleID;
        MineDisco.getPlugin(MineDisco.class).getConfig().set("discord.syncedChannelRoleID", channelRoleID);
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

    /**
     *
     * @return
     */
    public static boolean ChannelRoleIsSet() {
        if (discordChannelID.isEmpty() || "DEFAULTROLE".equals(channelRoleID)) {
            return false;
        }
        return true;
    }

        /**
     * @return the requestAccessChannelID
     */
    public static String getRequestAccessID() {
        return requestAccessChannelID;
    }

    /**
     * @param newDiscordChannelID the requestAccessChannelID to set
     */
    public static void setRequestAccessChannelID(String newDiscordChannelID) {
        requestAccessChannelID = newDiscordChannelID;
        MineDisco.getPlugin(MineDisco.class).getConfig().set("discord.requestAccessChannelID", requestAccessChannelID);
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

    /**
     *
     * @return
     */
    public static boolean requestAccessChannelIsSet() {
        if (discordChannelID.isEmpty() || "ACCESSREQUESTCHANNELID".equals(requestAccessChannelID)) {
            return false;
        }
        return true;
    }

}
