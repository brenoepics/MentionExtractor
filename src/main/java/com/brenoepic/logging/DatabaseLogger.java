package com.brenoepic.logging;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.AllowedMentions;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.brenoepic.MentionPlugin;
import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.brenoepic.MentionPlugin.LOGGER;

public class DatabaseLogger  {

    public static void save(List<Message> messages) {
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
                return;
            }finally {
                if (Emulator.getConfig().getBoolean("mentionplugin.logging_discord", true)) {
                    WebhookClient client = WebhookClient.withUrl(Emulator.getConfig().getValue("mentionplugin.logging.discord-webhook.url"));
                    WebhookMessageBuilder message = new WebhookMessageBuilder()
                            .setAllowedMentions(AllowedMentions.none());

                    StringBuilder msg = new StringBuilder()
                            .append("**[Mention-Plugin]**\n");
                    for (Message n : messages) {
                        msg.append("`FROM: ").append(n.getFromId()).append(" TO: ").append(n.getToUser()).append(" MESSAGE: ").append(n.getMessage()).append(" TIME: ").append(n.getTimestamp()).append("`\n");
                    }
                    message.append(msg.toString());
                        client.send(message.build());
                }
                MentionPlugin.dispose();
                LOGGER.debug("[MENTIONPLUGIN] Mentions saved successfully!");

            }
        }
    }
}
