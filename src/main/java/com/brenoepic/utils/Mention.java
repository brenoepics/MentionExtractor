package com.brenoepic.utils;

import java.util.Map;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.messenger.MessengerBuddy;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import gnu.trove.map.hash.THashMap;

public class Mention {
  /**
   * @author BrenoEpic
   */

   public static String Sanitize(String str){
    PolicyFactory policy = new HtmlPolicyBuilder()
    .toFactory();
    return policy.sanitize(str);
   }
  public static String Everyone(Habbo sender, String message) {
    THashMap < String, String > mention = new THashMap < > ();
    mention.put("display", "BUBBLE");
    mention.put("image", Emulator.getTexts().getValue("commands.cmd_mention_everyone.look").replace("%LOOK%", sender.getHabboInfo().getLook()));
    final Room room = sender.getHabboInfo().getCurrentRoom();
    if (room != null && Emulator.getConfig().getBoolean("commands.cmd_mention_everyone.follow.enabled")) {
      mention.put("linkUrl", "event:navigator/goto/" + sender.getHabboInfo().getCurrentRoom().getId());
    }

    mention.put("message", Emulator.getTexts().getValue("commands.cmd_mention_everyone.message").replace("%MESSAGE%", Sanitize(message)).replace("%SENDER%", sender.getHabboInfo().getUsername()));

    for (Map.Entry < Integer, Habbo > set: Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
      Habbo receiver = set.getValue();
      if (receiver.getHabboStats().blockStaffAlerts)
        continue;
      receiver.getClient().sendResponse(new BubbleAlertComposer("mention", mention));
    }
    return Emulator.getTexts().getValue("commands.cmd_mention.message.sent").replace("%RECEIVER%", "everyone");

  }

  public static String Friends(Habbo sender, String message) {
    THashMap < String, String > mention = new THashMap < > ();
    mention.put("display", "BUBBLE");
    mention.put("image", Emulator.getTexts().getValue("commands.cmd_mention.look").replace("%LOOK%", sender.getHabboInfo().getLook()));
    final Room room = sender.getHabboInfo().getCurrentRoom();
    if (room != null && Emulator.getConfig().getBoolean("commands.cmd_mention.follow.enabled")) {
      mention.put("linkUrl", "event:navigator/goto/" + sender.getHabboInfo().getCurrentRoom().getId());
    }
    mention.put("message", Emulator.getTexts().getValue("commands.cmd_mention.message").replace("%MESSAGE%", Sanitize(message)).replace("%SENDER%", sender.getHabboInfo().getUsername()));
    for (MessengerBuddy player: sender.getMessenger().getFriends().values()) {
      Habbo receiver = Emulator.getGameEnvironment().getHabboManager().getHabbo(player.getId());
      if (player.getOnline() == 0 || receiver == null)
        continue;
      receiver.getClient().sendResponse(new BubbleAlertComposer("mention", mention));
    }

    return Emulator.getTexts().getValue("commands.cmd_mention.message.sent").replace("%RECEIVER%", Emulator.getTexts().getValue("commands.cmd_mention.allfriends"));
  }

  public static String User(Habbo sender, Habbo receiver, String message) {
    THashMap < String, String > mention = new THashMap < > ();
    mention.put("display", "BUBBLE");
    mention.put("image", Emulator.getTexts().getValue("commands.cmd_mention.look").replace("%LOOK%", sender.getHabboInfo().getLook()));
    final Room room = sender.getHabboInfo().getCurrentRoom();
    if (room != null && Emulator.getConfig().getBoolean("commands.cmd_mention.follow.enabled")) {
      mention.put("linkUrl", "event:navigator/goto/" + sender.getHabboInfo().getCurrentRoom().getId());
    }
    mention.put("message", Emulator.getTexts().getValue("commands.cmd_mention.message").replace("%MESSAGE%", Sanitize(message)).replace("%SENDER%", sender.getHabboInfo().getUsername()));
    receiver.getClient().sendResponse(new BubbleAlertComposer("mention", mention));

    return Emulator.getTexts().getValue("commands.cmd_mention.message.sent");
  }
}