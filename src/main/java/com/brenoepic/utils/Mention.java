package com.brenoepic.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.messenger.MessengerBuddy;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;

import gnu.trove.map.hash.THashMap;

public class Mention {

  /**
   * @author BrenoEpic
   */

  public static String[] FRIENDS_PREFIX = Emulator.getConfig().getValue("commands.cmd_mention_friends.prefix", "friends;friends").split(";");
  public static int MENTION_MODE = Emulator.getConfig().getInt("mentionplugin.mode_user", 1);
  public static int EVERYONE_MODE = Emulator.getConfig().getInt("mentionplugin.mode_everyone", 1);
  public static int FRIENDS_MODE = Emulator.getConfig().getInt("mentionplugin.mode_friends", 1);
  public static int ROOM_MODE = Emulator.getConfig().getInt("mentionplugin.mode_room", 1);
  public static THashMap<String, String> mention;
  public static boolean run(Habbo sender, String receiver, String message) {
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
        }
        break;
      default:
        if (Arrays.stream(Mention.FRIENDS_PREFIX).anyMatch(receiver::equals) && sender.getHabboInfo().getRank().hasPermission("acc_mention_friends", true)) {
          for (MessengerBuddy player : sender.getMessenger().getFriends().values()) {
            Habbo user = Emulator.getGameEnvironment().getHabboManager().getHabbo(player.getId());
            if (player.getOnline() == 0 || user == null && user.getHabboStats().blockRoomInvites)
              continue;
            Mention.send(user, message, FRIENDS_MODE);
          }
          sender.whisper(Emulator.getTexts().getValue("commands.cmd_mention.message.sent").replace("%RECEIVER%", Emulator.getTexts().getValue("commands.cmd_mention.allfriends")));

        } else {
          Habbo user = Emulator.getGameEnvironment().getHabboManager().getHabbo(receiver);
          if (sender.getHabboInfo().getUsername().equals(user.getHabboInfo().getUsername())) {
            sender.whisper(Emulator.getTexts().getValue("commands.error.cmd_mention.not_self"));
            return false;
          }
          if (user == null || user.getHabboStats().allowNameChange && !sender.getHabboInfo().getRank().hasPermission("acc_mention", true)) {
            sender.whisper(Emulator.getTexts().getValue("commands.error.cmd_mention.user_not_found"));
            return false;
          }
          Mention.send(user, message, MENTION_MODE);
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