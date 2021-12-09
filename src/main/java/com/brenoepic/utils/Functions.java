package com.brenoepic.utils;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import gnu.trove.map.hash.THashMap;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Functions {

    public static String Sanitize(String str){
        PolicyFactory policy = new HtmlPolicyBuilder()
        .toFactory();
        return policy.sanitize(str);
       }
       
    public static Set<String> getUserMentionedFromChat(String chat) {
        Set<String> Mentioned = new HashSet();
        Pattern compiledPattern = Pattern.compile(Emulator.getConfig().getValue("commands.cmd_mention_regex", "@(\\w+)"));
        Matcher matcher = compiledPattern.matcher(chat);
            
            while(matcher.find()) {
                if(Mentioned.size() < Emulator.getConfig().getInt("commands.cmd_mention_max", 5))
                Mentioned.add(matcher.group(1));
            }
        
        return Mentioned;
    }
    public static THashMap<String, String> BubbleAlert(Habbo sender, String message){
        if(Emulator.getConfig().getBoolean("mentionplugin.sanitize", true))
            message = Sanitize(message);
        THashMap<String, String> notification = new THashMap<>();
        notification.put("display", "BUBBLE");
        notification.put("image", Emulator.getTexts().getValue("commands.cmd_mention_everyone.look").replace("%LOOK%", sender.getHabboInfo().getLook()));
        final Room room = sender.getHabboInfo().getCurrentRoom();
        if (room != null && Emulator.getConfig().getBoolean("commands.cmd_mention_everyone.follow.enabled")) {
            notification.put("linkUrl", "event:navigator/goto/" + sender.getHabboInfo().getCurrentRoom().getId());
        }
        return notification;
    }
}
