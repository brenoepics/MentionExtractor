package com.brenoepic.events;

import com.brenoepic.utils.Mention;

import java.util.Set;

import com.brenoepic.utils.Functions;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.events.users.UserTalkEvent;


public class MentionUser implements EventListener {
  @EventHandler
  public static void onUserTalkEvent(UserTalkEvent event) throws Exception {
    Set<String> GetMention = Functions.getUserMentionedFromChat(event.chatMessage.getMessage());
    if (!GetMention.isEmpty()) {
        for (String userMentioned : GetMention) {
            String message = event.chatMessage.getMessage();
            Habbo sender = event.chatMessage.getHabbo();

                if (!Emulator.getConfig().getBoolean("commands.cmd_mention.message.show_username.enabled"))
                    message = event.chatMessage.getMessage().replaceFirst("@" + userMentioned, "");

                boolean mentioned = Mention.send(sender, userMentioned, message);
                if (mentioned) {
                    //Logger service
                }

            if (Emulator.getConfig().getBoolean("commands.cmd_mention.message.delete"))
                event.setCancelled(true);
        }
    }
  }
}