package com.brenoepic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author skeletor#0001
 * thanks skeletor for the regex of the javascript plugin.
 */
public class RegexUtility {


    public static String getUserMentionedFromChat(String chat) {
        String pattern = "@(\\w+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(chat);
        if(matcher.find() && matcher.groupCount() > 0) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
