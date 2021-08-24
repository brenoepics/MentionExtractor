# MentionPlugin 2.0

Working with Arcturus Morningstar 3.0.0

if you want a more simple version, here is the link: https://git.krews.org/brenoepic/mentionplugin

if you want a pre-compiled version, here is the link: https://github.com/brenoepics/MentionPlugin/releases/
## How to use?
you just need to send:
```@username``` or ```@friends``` or ```@everyone```
with a message and your friend will receive it.
- Mention with @username at the beginning of the message [x] 
- Mention in anywhere in the message [x]
- Mention all friends with @friends [x] 
- Mention everyone with @everyone [x] 
- Mention more than one user in the same message [x]
- BlockMention Command [TODO]

<b>with this mention plugin, you can mention anyone without putting the @ at the beginning of the message, the @ can be anywhere</b>

## How can i install it?

 1. Download a pre-compiled version [MentionPlugin.jar](https://github.com/brenoepics/MentionPlugin/raw/master/target/MentionPlugin-2.0.jar)
 2. Paste the MentionPlugin.jar file into your emulator's plugins folder and start/restart the emulator.
 3. Now you need to give permission for your users to use @mention, to do it open your database in the permissions table, and change the acc_mention, acc_mention_everyone and acc_mention_friends.
 4. Then, enter your hotel and type: :update_permissions or restart the emulator.
 5. Now just mention a friend or maybe everyone :)


## Translation and configuration
Emulator_texts:
| Key | Default Value |
| ------ | ------ |
| commands.error.cmd_mention.not_self | You cannot mention yourself |
| commands.cmd_mention.message | %SENDER% said: %MESSAGE% |
| commands.cmd_mention.message.sent | You mentioned %RECEIVER% successfully! |
| commands.error.cmd_mention.user_not_found | Sorry, I could not find the mentioned user. |
| commands.cmd_mention.allfriends | all friends |
| commands.cmd_mention_everyone.message | %SENDER% said: %MESSAGE% |
| commands.cmd_mention_everyone.look | ${image.library.url}notifications/fig/%LOOK%.png |
| commands.cmd_mention.look | ${image.library.url}notifications/fig/%LOOK%.png |

 Emulator_settings:
 | Key | Default Value |
| ------ | ------ |
| commands.cmd_mention_friends.prefix | friends |
| commands.cmd_mention.message.delete | 0 |
| commands.cmd_mention.follow.enabled | 1 |
| commands.cmd_mention.message.show_username.enabled | 1 |
| commands.cmd_mention_everyone.follow.enabled | 1 |
| commands.cmd_mention_regex | @(\\w+) |

If you need to enable special characters modify the "commands.cmd_mention_regex", for example using this regex:  ```"@([\w'"!]+)"``` you can have ```!'"``` in your mention.

https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html?is-external=true


 Permissions:
  | Key | Default Value |
| ------ | ------ |
| acc_mention | 0 |
| acc_mention_friends | 0 |
| acc_mention_everyone | 0 |

      Compile with dependencies ```mvn clean compile assembly:single```
My Discord: BrenoEpic#9671
