# Minecraft-Discord-Bot-Bukkit
Minecraft Bukkit plugin which integrates Minecraft chat to the Discord channel and vice versa. 

## Features 
* Messages from Minecraft chat can be delivered to the selected Discord channel
* Messages from selected Discord channel can be delivered to the Minecraft Chat
* Different types of messages can be turned off by using commands or by editing the config file 

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
   - Select bot permissions (at least "Send messages" and "Read message history") 
   - Copy URL and go to it 
   - Select server where you want the bot to join and authorize it (If you do not see server in the list, you might not be an owner of that server)
9. Give bot rights to see and write to the channel which you want to integrate at the Discord
10. If bot is running at the server and it has correct token, you can write command "!set integratedchannel"
11. If bot answers, your discord channel is now integrated with your Minecraft chat 

## Configuration file
```yml
discord:
  botToken: "DEFAULTTOKEN" # Discord bots authentication token
  channelID: "DEFAULTCHANNELID" # Discord channel id which is integrated with the Minecraft channel
  commandPrefix: "!" # Prefix that has to be used before bot commands

integration:
  mincraftChatToDiscord: true       # Enables message integration from Minecraft chat to Discord channel
  discordToMinecraftChat: true      # Enables messages integration from Discord channel to Minecraft chat 
  deathMessagesToDiscord: true      # Enables death notices to be delivered to Discord channel
  joinQuitMessagesToDiscord: true   # Enables join/quit notices to be delivered to Discord channel
  serverSayMessagesToDiscord: true  # Enables server say messages to be delivered to Discord channel
  advancementsToDiscord: true       # Enables join/quit notices to be delivered to Discord channel
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









