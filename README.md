# Minecraft-Discord-Bot-Bukkit
![](https://img.shields.io/github/release/MineDisco/Minecraft-Discord-Bot-Bukkit.svg?style=flat-square)
![](https://img.shields.io/badge/Minecraft-1.16.2-blue.svg?style=flat-square)

Minecraft Bukkit plugin which integrates Minecraft to the Discord and vice versa. 

## Features 
* Messages from Minecraft chat can be delivered to the selected Discord channel
* Messages from selected Discord channel can be delivered to the Minecraft Chat
* Different types of messages can be turned off by using commands or by editing the config file 
* Minecraft users can be authenticated using Discord 
* Existing users vote if new user should be let in to the server on the Discord
![](https://raw.githubusercontent.com/MineDisco/Minecraft-Discord-Bot-Bukkit/WhitelistFromDiscord/Vote%20Example.PNG)


### Also following can set to be sent to the Discord channel:
* Death notices
* Advancement messages
* Join/quit messages (with a count of the current players online) 
* Server "say" messages from the server console

## Requirements
* Minecraft server which supports Bukkit plugins
* Be an owner of a Discord server

## Setup
1. Download latest plugin .jar from the releases and place it to the plugin folder of your server
2. When you start server first time with the plugin it generates default config file (explained below) 
3. Go to https://discordapp.com/developers/
4. Create new application
5. Create new bot for the application (Bot -> Add bot)
6. Copy bot token to the config.yml
7. Restart Minecraft Server
8. Invite it to your server: 
   - Click "OAuth" 
   - Select "bot" 
   - Select bot permissions (at least "Administrator") 
   - Copy URL and go to it 
   - Select server where you want the bot to join and authorize it (If you do not see server in the list, you might not be an owner of that server)
9. If bot is running at the server and it has correct token, you can write command "!set integratedchannel" on the textchannel which you want integrate with Minecraft chat
10. Select channel were you want access voting messages to appear. (You should change channel permission to allow only readin channel and its history and disallow everything else)
11. Create role to the server that has the access to the integrated channel and access requesting channel 
12. Copy role's ID (Enable development mode from the Discord settings and then right click role name) and activate it to the bot by editing config file or using command "!set role ROLEIDHERE"

## Configuration file
```yml
discord:
  botToken: "DEFAULTTOKEN" # Discord bots authentication token
  channelID: "DEFAULTCHANNELID" # Discord channel id which is integrated with the Minecraft channel
  requestAccessChannelID: "ACCESSREQUESTCHANNELID" # Discord channel id of the channel were already allowed users vote if new user should be let in to the server
  syncedChannelRoleID: "DEFAULTROLE" # Discord role id of the role that have access to integrated Discord and request voting channel 
  commandPrefix: "!" # Prefix that has to be used before bot commands


integration:
  mincraftChatToDiscord: true       # Enables message integration from Minecraft chat to Discord channel
  discordToMinecraftChat: true      # Enables messages integration from Discord channel to Minecraft chat 
  deathMessagesToDiscord: true      # Enables death notices to be delivered to Discord channel
  joinQuitMessagesToDiscord: true   # Enables join/quit notices to be delivered to Discord channel
  serverSayMessagesToDiscord: true  # Enables server say messages to be delivered to Discord channel
  advancementsToDiscord: true       # Enables join/quit notices to be delivered to Discord channel
  discordWhitelist: true            # Enables Discord whitelist (remember to disable original whitelist from the server settings)
  serverSayMessageFilterPrefix: '!' # Filters server say messages that begins with this prefix
```
## Commands
Only Discord server owner can use these commands.

| Command        | Explanation |
| ------------- |--------------|
| `!set integratedchannel` | **Integrates Discord channel where command was send with the Minecraft chat**|
| `!set prefix NEWPREFIX` | **Changes prefix used before commands. Default is "!"** |
| `!set minecraftchattodiscord`  | **Enable/disable message flow from the Minecraft chat to the Discord** |
| `!set discordtominecraftchat ` | **Enable/disable message flow from the Discord channel to the Minecraft** |
| `!set deadMessagesToDiscord` | **Enable/disable death notices to the Discord channel** |
| `!set joinQuitMessagesToDiscord ` | **Enable/disable join/quit messages to the Discord channel** |
| `!set serverSayMessagesToDiscord ` | **Enable/disable server console "say"-messages to the Discord channel** |
| `!set advancementstodiscord ` | **Enable/disable advancement messages to the Discord channel** |
| `!set accessrequestchannel ` | **Set channel were bot should post access voting messages** |
| `!set role ROLEID ` | **Set role id which bot should add to authenticated user to get access to the other Minecraft Discord channels** |









