package com.brenoepic.events;

import com.brenoepic.utils.Mention;
import com.brenoepic.utils.RegexUtility;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.events.users.UserTalkEvent;


public class MentionUser implements EventListener {
  @EventHandler
  public static void onUserTalkEvent(UserTalkEvent event) throws Exception {

    String userMentioned = RegexUtility.getUserMentionedFromChat(event.chatMessage.getMessage());
    if (!userMentioned.isEmpty()) {
      String message;
      Habbo sender = event.chatMessage.getHabbo();
      if (userMentioned.equals("everyone") && event.chatMessage.getHabbo().hasPermission("acc_mention_everyone")) {

        if (Emulator.getConfig().getBoolean("commands.cmd_mention.message.show_username.enabled")) {
          message = event.chatMessage.getMessage();
        } else {
          message = event.chatMessage.getMessage().replaceFirst("@everyone", "");
        }

        String Everyone = Mention.Everyone(sender, message);
        sender.getClient().getHabbo().whisper(Everyone, RoomChatMessageBubbles.ALERT);

      } else {
        if (userMentioned.equals(Emulator.getConfig().getValue("commands.cmd_mention_friends.prefix")) && event.chatMessage.getHabbo().hasPermission("acc_mention_friends")) {

          if (Emulator.getConfig().getBoolean("commands.cmd_mention.message.show_username.enabled")) {
            message = event.chatMessage.getMessage();
          } else {
            message = event.chatMessage.getMessage().replaceFirst("@" + Emulator.getConfig().getValue("commands.cmd_mention_friends.prefix"), "");
          }
          String Friends = Mention.Friends(sender, message);
          sender.getClient().getHabbo().whisper(Friends, RoomChatMessageBubbles.ALERT);
        } else {
          if (event.chatMessage.getHabbo().hasPermission("acc_mention")) {
            Habbo receiver = Emulator.getGameEnvironment().getHabboManager().getHabbo(userMentioned);
            if (receiver != null) {
              if (sender.getHabboInfo().getUsername().equals(receiver.getHabboInfo().getUsername())) {
                sender.whisper(Emulator.getTexts().getValue("commands.error.cmd_mention.not_self"));
              } else {
                if (Emulator.getConfig().getBoolean("commands.cmd_mention.message.show_username.enabled")) {
                  message = event.chatMessage.getMessage();
                } else {
                  message = event.chatMessage.getMessage().replaceFirst("@" + userMentioned, "");
                }

                String User = Mention.User(sender, receiver, message);
                sender.getClient().getHabbo().whisper(User.replace("%RECEIVER%", userMentioned), RoomChatMessageBubbles.ALERT);
              }
            } else {
              sender.whisper(Emulator.getTexts().getValue("commands.error.cmd_mention.user_not_found"));
            }
          }
        }

      }
      if (Emulator.getConfig().getBoolean("commands.cmd_mention.message.delete")) {
        event.chatMessage.setMessage("");
      }

    }

  }
}