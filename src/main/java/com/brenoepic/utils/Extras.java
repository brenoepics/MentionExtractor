package com.brenoepic.utils;

import com.brenoepic.commands.BlockMentionCommand;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Extras {
  public static void loadTexts() {
    Emulator.getTexts().register("commands.error.cmd_mention.not_self", "You cannot mention yourself");
    Emulator.getTexts().register("commands.cmd_mention.message", "%SENDER% said: %MESSAGE%");
    Emulator.getTexts().register("commands.cmd_mention.message.sent", "You mentioned %RECEIVER% successfully!");
    Emulator.getTexts().register("commands.error.cmd_mention.user_not_found", "Sorry, I could not find the mentioned user.");
    Emulator.getTexts().register("commands.cmd_mention_everyone.message", "%SENDER% said: %MESSAGE%");
    Emulator.getTexts().register("commands.cmd_mention.allfriends", "all friends");
    Emulator.getTexts().register("commands.cmd_mention.allfriends", "whole room");
    Emulator.getTexts().register("commands.cmd_mention.look", "${image.library.url}notifications/fig/%LOOK%.png");
    Emulator.getTexts().register("commands.cmd_mention_everyone.look", "${image.library.url}notifications/fig/%LOOK%.png");
    Emulator.getTexts().register("commands.error.cmd_mention.user_blocksmention", "The user does not want to be mentioned.");
    Emulator.getTexts().register("cmd_blockmention_keys", "blockmention;mentionsoff");
    Emulator.getConfig().register("commands.description.cmd_blockmention", ":blockmention");
    Emulator.getConfig().register("commands.cmd_mention_friends.prefix", "friends");
    Emulator.getConfig().register("commands.cmd_mention.message.delete", "0");
    Emulator.getConfig().register("commands.cmd_mention.follow.enabled", "1");
    Emulator.getConfig().register("commands.cmd_mention.message.show_username.enabled", "1");
    Emulator.getConfig().register("commands.cmd_mention_everyone.follow.enabled", "1");
    Emulator.getConfig().register("commands.cmd_mention_regex", "@(\\w+)");
    Emulator.getConfig().register("commands.cmd_mention_max", "5");
    Emulator.getConfig().register("mentionplugin.sanitize", "1");
    Emulator.getConfig().register("mentionplugin.mode_user", "1");
    Emulator.getConfig().register("mentionplugin.mode_everyone", "1");
    Emulator.getConfig().register("mentionplugin.mode_friends", "1");
    Emulator.getConfig().register("mentionplugin.mode_room", "2");

    CommandHandler.addCommand(new BlockMentionCommand("cmd_blockmention", Emulator.getTexts().getValue("cmd_blockmention_keys").split(";")));
   }

  private static boolean registerPermission(String name, boolean defaultReturn) {
    try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name + "` ENUM('0', '1') NOT NULL DEFAULT '0'")) {
        statement.execute();
        return true;
      }
    }catch (SQLException sql) {
      //To do (when the stable version of the arcturus has a function to add a permission I will change this method)
    }

  return defaultReturn;

  }

  public static void checkDatabase() {
    boolean reloadPermissions = registerPermission("acc_mention", false);
    reloadPermissions = registerPermission("acc_mention_friends", reloadPermissions);
    reloadPermissions = registerPermission("acc_mention_everyone", reloadPermissions);
    reloadPermissions = registerPermission("acc_mention_room", reloadPermissions);
    if (reloadPermissions)
      Emulator.getGameEnvironment().getPermissionsManager().reload();
  }
}