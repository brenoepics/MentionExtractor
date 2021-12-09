# MentionPlugin

Working with Arcturus Morningstar 3.0.0

if you want a more simple version, here is the link: https://git.krews.org/brenoepic/mentionplugin

if you want a pre-compiled version, here is the link: https://github.com/brenoepics/MentionPlugin/releases/
## How to use?
you just need to send:
```@username``` , ```@friends``` , ```@everyone``` or ```@room```
with a message.
- Mention with @username at the beginning of the message [x] 
- Mention in anywhere in the message [x]
- Mention all friends with @friends [x] 
- Mention everyone with @everyone [x] 
- Mention whole room with @room [x]
- BlockMention Command [x]
- Set Mention Timeout [x]
- Choose between BubbleAlert or whisper [x]
- Database logging [x]
- Discord logging [x]
<b>with this mention plugin, you can mention anyone without putting the @ at the beginning of the message, the @ can be anywhere</b>

## How can i install it?

 1. Download a pre-compiled version. [MentionPlugin](https://github.com/brenoepics/MentionPlugin/releases/)
 2. Run the sql.
 3. Paste the MentionPlugin-2.2-jar-with-dependencies.jar file into your emulator's plugins folder and start/restart the emulator.
 4. Now you need to give permission for your users to use @mention, to do it open your database in the permissions table, and change the acc_mention, acc_mention_everyone, acc_mention_friends, acc_mention_room.
 5. Then, enter your hotel and type: :update_permissions or restart the emulator.
 6. Now just mention a friend or maybe everyone :)


## configuration

 Emulator_settings:

| Key | Default Value             |
| ------ |---------------------------|
| commands.cmd_mention_friends.prefix | friends                   |
| commands.cmd_mention.message_error.delete | true                      |
| commands.cmd_mention.message_success.delete | false                     |
| commands.cmd_mention.follow.enabled | true                      |
| commands.cmd_mention.message.show_username.enabled | true                      |
| commands.cmd_mention_everyone.follow.enabled | true                      |
| commands.cmd_mention_regex | @(\\w+)                   |
| commands.cmd_mention_max | 5 (max users in same msg) |
| mentionplugin.sanitize | true                      |
| mentionplugin.mode_user | 1 (1 bubble 2 whisper)    |
| mentionplugin.mode_everyone | 1 (1 bubble 2 whisper)    |
| mentionplugin.mode_friends | 1 (1 bubble 2 whisper)    |
| mentionplugin.timeout_user | 10                        |
| mentionplugin.timeout_everyone | 5                         |
| mentionplugin.timeout_friends | 60                        | 
| mentionplugin.timeout_room | 20                        | 
| mentionplugin.logging_database | true                      |
| mentionplugin.database.log_timeout_minutes | 30                        |

 Permissions:

| Key                  | Default Value |
|----------------------|---------------|
| acc_mention          | 1             |
| acc_mention_friends  | 1             |
| acc_mention_everyone | 0             |
| acc_mention_room     | 2             |
| cmd_blockmention     | 1             |

        
My Discord: BrenoEpic#9671
