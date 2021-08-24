package com.brenoepic.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brenoepic.MentionPlugin;
import com.eu.habbo.Emulator;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;


public class Functions {
    
    public static String Sanitize(String str){
        PolicyFactory policy = new HtmlPolicyBuilder()
        .toFactory();
        return policy.sanitize(str);
       }
       
    public static Set<String> getUserMentionedFromChat(String chat) {
        Set<String> Mentioneds = new HashSet();
        Pattern compiledPattern = Pattern.compile(Emulator.getConfig().getValue("commands.cmd_mention_regex", "@(\\w+)"));
        Matcher matcher = compiledPattern.matcher(chat);
            
            while(matcher.find()) {
                if(Mentioneds.size() < Emulator.getConfig().getInt("commands.cmd_mention_max", 5))
                Mentioneds.add(matcher.group(1));
            }
        
        return Mentioneds;
    }
}
