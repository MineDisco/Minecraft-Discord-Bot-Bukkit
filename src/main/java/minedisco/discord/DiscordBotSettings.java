package minedisco.discord;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import minedisco.MineDisco;

/**
 *
 */
@ParametersAreNonnullByDefault
public class DiscordBotSettings {
    @Nonnull
    private static String discordChannelID = "";
    @Nonnull
    private static String requestAccessChannelID = "";
    private static boolean enabledDiscordtoMinecraftChat = true;
    @Nonnull
    private static String channelRoleID = "";
    @Nonnull
    private static String serverName = "";
    @Nonnull
    private static String statusChannelID = "";
    @Nonnull
    private static String statusMessageID = "";

    /**
     *
     */
    public DiscordBotSettings() {
        String chid = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.channelID");
        if (chid != null
                && !chid.isEmpty()) {
            discordChannelID = chid;
        }

        enabledDiscordtoMinecraftChat = MineDisco.getPlugin(MineDisco.class).getConfig()
                .getBoolean("integration.discordToMinecraftChat");

        String rchid = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.requestAccessChannelID");
        if (rchid != null) {
            requestAccessChannelID = rchid;
        }

        String schid = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.syncedChannelRoleID");
        if (schid != null) {
            channelRoleID = schid;
        }    

        String sname = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.serverName");
        if (sname != null) {
            setServerName(sname);
        }

        String stchid= MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.statusChannelID");
        if (stchid != null) {
            setStatusChannelID(stchid);
        }

        String smeid = MineDisco.getPlugin(MineDisco.class).getConfig().getString("discord.statusMessageID");
        if (smeid != null) {
            statusMessageID = smeid;
        }  
    }

    @Nonnull
    public static String getServerName() {
        if (serverName == null || serverName.isEmpty()) {
            return "";
        }

        return serverName + ": ";
    }

    public static void setServerName(@Nonnull String serverName) {
        DiscordBotSettings.serverName = serverName;
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
    @Nonnull
    public static String getDiscordChannelID() {
        return discordChannelID;
    }

    /**
     * @param newDiscordChannelID the discordChannelID to set
     */
    public static void setDiscordChannelID(@Nonnull String newDiscordChannelID) {
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
     *
     * @return
     */
    public static boolean discordStatusChannelIsSet() {
        if (statusChannelID.isEmpty() || "STATUSCHANNELID".equals(statusChannelID)) {
            return false;
        }
        return true;
    }

    public static boolean discordStatusMessageIsSet() {
        if (statusMessageID.isEmpty() || "STATUSMESSAGEID".equals(statusMessageID)) {
            return false;
        }
        return true;
    }

    /**
     * @return the discordChannelID
     */
    @Nonnull
    public static String getchannelRoleID() {
        return channelRoleID;
    }

    /**
     * @param newDiscordChannelID the discordChannelID to set
     */
    public static void setChannelRoleID(@Nonnull String newChannelRoleID) {
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
    @Nonnull
    public static String getRequestAccessID() {
        return requestAccessChannelID;
    }

    /**
     * @param newDiscordChannelID the requestAccessChannelID to set
     */
    public static void setRequestAccessChannelID(@Nonnull String newDiscordChannelID) {
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

    @Nonnull
    public static String getStatusChannelID() {
        return statusChannelID;
    }

    public static void setStatusChannelID(@Nonnull String statusChannelID) {
        DiscordBotSettings.statusChannelID = statusChannelID;
        MineDisco.getPlugin(MineDisco.class).getConfig().set("discord.statusChannelID", statusChannelID);
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

    @Nonnull
    public static String getStatusMessageID() {
        return statusMessageID;

    }

    public static void setStatusMessageID(@Nonnull String statusMessageID) {
        DiscordBotSettings.statusMessageID = statusMessageID;
        MineDisco.getPlugin(MineDisco.class).getConfig().set("discord.statusMessageID", statusMessageID);
        MineDisco.getPlugin(MineDisco.class).saveConfig();
    }

}
