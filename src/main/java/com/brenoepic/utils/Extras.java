package com.brenoepic.utils;

import com.eu.habbo.Emulator;

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
    Emulator.getTexts().register("commands.cmd_mention.look", "${image.library.url}notifications/fig/%LOOK%.png");
    Emulator.getTexts().register("commands.cmd_mention_everyone.look", "${image.library.url}notifications/fig/%LOOK%.png");
  
    Emulator.getConfig().register("commands.cmd_mention_friends.prefix", "friends");
    Emulator.getConfig().register("commands.cmd_mention.message.delete", "0");
    Emulator.getConfig().register("commands.cmd_mention.follow.enabled", "1");
    Emulator.getConfig().register("commands.cmd_mention.message.show_username.enabled", "1");
    Emulator.getConfig().register("commands.cmd_mention_everyone.follow.enabled", "1");
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
    boolean reloadPermissions = false;
    reloadPermissions = registerPermission("acc_mention", reloadPermissions);
    reloadPermissions = registerPermission("acc_mention_friends", reloadPermissions);
    reloadPermissions = registerPermission("acc_mention_everyone", reloadPermissions);

    if (reloadPermissions)
      Emulator.getGameEnvironment().getPermissionsManager().reload();
  }
}