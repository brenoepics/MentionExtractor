package com.brenoepic.utils;

import com.brenoepic.MentionPlugin;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.messenger.MessengerBuddy;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

import java.util.Arrays;
import java.util.Map;

public class Mention {
  public static String[] FRIENDS_PREFIX = Emulator.getConfig().getValue("commands.cmd_mention_friends.prefix", "friends;friends").split(";");

  public static int MENTION_MODE = Emulator.getConfig().getInt("mentionplugin.mode_user", 1);
  public static int EVERYONE_MODE = Emulator.getConfig().getInt("mentionplugin.mode_everyone", 1);
  public static int FRIENDS_MODE = Emulator.getConfig().getInt("mentionplugin.mode_friends", 1);
  public static int ROOM_MODE = Emulator.getConfig().getInt("mentionplugin.mode_room", 1);

  public static int MENTION_TIMEOUT = Emulator.getConfig().getInt("mentionplugin.timeout_user", 10);
  public static int EVERYONE_TIMEOUT = Emulator.getConfig().getInt("mentionplugin.timeout_everyone", 10);
  public static int FRIENDS_TIMEOUT = Emulator.getConfig().getInt("mentionplugin.timeout_friends", 10);
  public static int ROOM_TIMEOUT = Emulator.getConfig().getInt("mentionplugin.timeout_room", 10);

  public static THashMap<String, String> mention;
  public static boolean run(Habbo sender, String receiver, String message) {
    if(!MentionPlugin.getTimeout().CanMention(sender.getHabboInfo().getId())){
      sender.whisper(Emulator.getTexts().getValue("mentionplugin.timeout_message").replace("%time%", "5"));
      return false;
    }
    mention = Functions.BubbleAlert(sender, message);

    switch (receiver) {
      case "everyone":
        if (sender.getHabboInfo().getRank().hasPermission("acc_mention_everyone", false)) {
          for (Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
            Habbo user = set.getValue();
            if (user.getHabboStats().blockStaffAlerts)
              continue;
            Mention.send(user, message, EVERYONE_MODE);
          }
          MentionPlugin.getTimeout().Add(sender.getHabboInfo().getId(), EVERYONE_TIMEOUT);
          sender.whisper(Emulator.getTexts().getValue("commands.cmd_mention.message.sent").replace("%RECEIVER%", "everyone"));
        }
        break;
      case "room":
        if (sender.getHabboInfo().getRank().hasPermission("acc_mention_room", true)) {
          for (Habbo user : sender.getRoomUnit().getRoom().getHabbos()) {

            if (user == null)
              continue;
            Mention.send(user, message, ROOM_MODE);
          }
          sender.whisper(Emulator.getTexts().getValue("commands.cmd_mention.message.sent").replace("%RECEIVER%", Emulator.getTexts().getValue("commands.cmd_mention.room", "room")));
          MentionPlugin.getTimeout().Add(sender.getHabboInfo().getId(), ROOM_TIMEOUT);
        }
        break;
      default:
        if (Arrays.asList(Mention.FRIENDS_PREFIX).contains(receiver) && sender.getHabboInfo().getRank().hasPermission("acc_mention_friends", true)) {
          for (MessengerBuddy player : sender.getMessenger().getFriends().values()) {
            Habbo user = Emulator.getGameEnvironment().getHabboManager().getHabbo(player.getId());
            if (player.getOnline() == 0 || user == null || user.getHabboStats().blockRoomInvites)
              continue;
            Mention.send(user, message, FRIENDS_MODE);
          }
          sender.whisper(Emulator.getTexts().getValue("commands.cmd_mention.message.sent").replace("%RECEIVER%", Emulator.getTexts().getValue("commands.cmd_mention.allfriends")));
          MentionPlugin.getTimeout().Add(sender.getHabboInfo().getId(), FRIENDS_TIMEOUT);
        } else {
          Habbo user = Emulator.getGameEnvironment().getHabboManager().getHabbo(receiver);
          if (user == null || !sender.getHabboInfo().getRank().hasPermission("acc_mention", true)) {
            sender.whisper(Emulator.getTexts().getValue("commands.error.cmd_mention.user_not_found"));
            return false;
          }
          if (sender.getHabboInfo().getUsername().equals(user.getHabboInfo().getUsername())) {
            sender.whisper(Emulator.getTexts().getValue("commands.error.cmd_mention.not_self"));
            return false;
          }

          if(user.getHabboStats().cache.getOrDefault("blockmention", "0") == "1"){
            sender.whisper(Emulator.getTexts().getValue("commands.error.cmd_mention.user_blocksmention"));
            return false;
          }
          if(user.getHabboStats().userIgnored(sender.getHabboInfo().getId()))
            return false;

          Mention.send(user, message, MENTION_MODE);
          MentionPlugin.getTimeout().Add(sender.getHabboInfo().getId(), MENTION_TIMEOUT);
          sender.whisper(Emulator.getTexts().getValue("commands.cmd_mention.message.sent"));
        }
    }
    return true;
  }

  public static void send(Habbo receiver, String message, int mode ) {
    switch (mode) {
      case 1:
        receiver.getClient().sendResponse(new BubbleAlertComposer("mention", mention));
        break;
      case 2:
        receiver.whisper(message);
    }
  }
}