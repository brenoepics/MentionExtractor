package com.brenoepic.events;

import com.brenoepic.MentionPlugin;
import com.brenoepic.logging.Message;
import com.brenoepic.utils.Mention;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.brenoepic.utils.Functions;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.events.users.UserDisconnectEvent;
import com.eu.habbo.plugin.events.users.UserLoginEvent;
import com.eu.habbo.plugin.events.users.UserTalkEvent;


public class UserEvents implements EventListener {
  @EventHandler
  public static void onUserTalkEvent(UserTalkEvent event){
    Set<String> GetMention = Functions.getUserMentionedFromChat(event.chatMessage.getMessage());
    if (!GetMention.isEmpty()) {
        boolean mentioned;
        String message = event.chatMessage.getMessage();
        Habbo sender = event.chatMessage.getHabbo();
        if (!Emulator.getConfig().getBoolean("commands.cmd_mention.message.show_username.enabled"))
            message = event.chatMessage.getMessage().replace("@" + GetMention, "");

        Set<String> no = Collections.singleton("everyone");
        no.add("room");
        no.addAll(Arrays.asList(Mention.FRIENDS_PREFIX));
        if(GetMention.contains(no)){
            mentioned = Mention.custom(sender, GetMention.iterator().next(), message);
            if (mentioned) {
                if (Emulator.getConfig().getBoolean("commands.cmd_mention.message_success.delete"))
                    event.setCancelled(true);
                MentionPlugin.addMessage(new Message(sender.getHabboInfo().getId(), GetMention.iterator().next(), event.chatMessage.getMessage()));
                //TODO Discord Logging
            }else{
                if (Emulator.getConfig().getBoolean("commands.cmd_mention.message_error.delete"))
                    event.setCancelled(true);
            }
            GetMention.removeAll(no);
        }



                mentioned = Mention.user(sender, GetMention, message);
                if (mentioned) {
                    if (Emulator.getConfig().getBoolean("commands.cmd_mention.message_success.delete"))
                        event.setCancelled(true);
                    for (String userMentioned : GetMention) {
                    MentionPlugin.addMessage(new Message(sender.getHabboInfo().getId(), userMentioned, event.chatMessage.getMessage()));
                    }
                //TODO Discord Logging
                }else{
                    if (Emulator.getConfig().getBoolean("commands.cmd_mention.message_error.delete"))
                        event.setCancelled(true);
                }


    }
  }

  @EventHandler
    public static void onUserLoginEvent(UserLoginEvent event){
      if(event.habbo != null){
          try (final Connection connection = Emulator.getDatabase().getDataSource().getConnection();
               final PreparedStatement statement = connection.prepareStatement("SELECT `blockmentions` FROM `users_settings` WHERE `user_id` = ? LIMIT 1")) {
              statement.setInt(1, event.habbo.getHabboInfo().getId());
              try (final ResultSet set = statement.executeQuery()) {
                  if (set.next()) {
                      event.habbo.getHabboStats().cache.put("blockmention", set.getString("blockmentions").equals("1"));
                  }
              }
          }
          catch (SQLException e) {
              MentionPlugin.LOGGER.error("[MentionPlugin]", e);
          }
      }else{
          event.habbo.getHabboStats().cache.put("blockmention", false);
      }

  }
  @EventHandler
    public static void onUserDisconnectEvent(UserDisconnectEvent e){
      final boolean blockmention = (boolean) e.habbo.getHabboStats().cache.get("blockmention");
      try (final Connection connection = Emulator.getDatabase().getDataSource().getConnection();
           final PreparedStatement statement = connection.prepareStatement("UPDATE `users` SET `blockmentions` = ? WHERE `id` = ? LIMIT 1")) {
          statement.setString(1, blockmention ? "1" : "0");
          statement.setInt(2, e.habbo.getHabboInfo().getId());
          statement.executeUpdate();
      }
      catch (SQLException ex) {
          ex.printStackTrace();
      }
  }

}