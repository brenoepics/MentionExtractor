package com.brenoepic.logging;

import com.brenoepic.MentionPlugin;
import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.brenoepic.MentionPlugin.LOGGER;

public class DatabaseLogger  {

    public static boolean save(List<Message> messages) {
        if (Emulator.getConfig().getBoolean("mentionplugin.logging_database", true)) {
            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO mention_logs (user_from_id, user_to, message, timestamp) VALUES (?, ?, ?, ?)")) {
                for (Message n : messages)
                {
                    statement.setInt(1, n.getFromId());
                    statement.setString(2, n.getToUser());
                    statement.setString(3, n.getMessage());
                    statement.setInt(4, n.getTimestamp());
                    statement.execute();
                }
            }
            catch (SQLException e) {
                LOGGER.error("Caught SQL exception", e);
                return false;
            }finally {
                LOGGER.debug("[MENTIONPLUGIN] Mentions saved successfully!");
                MentionPlugin.dispose();
            }
        }
        return true;
    }
}
