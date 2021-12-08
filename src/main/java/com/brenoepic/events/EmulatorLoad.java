package com.brenoepic.events;

import com.brenoepic.logging.DatabaseLogger;
import com.eu.habbo.Emulator;
import com.brenoepic.MentionPlugin;
import com.brenoepic.utils.Extras;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.emulator.EmulatorStartShutdownEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.brenoepic.MentionPlugin.LOGGER;


public class EmulatorLoad implements EventListener {
    @EventHandler
    public static void onEmulatorLoaded(EmulatorLoadedEvent event) {
        Extras.checkDatabase();
        Extras.loadTexts();
        Emulator.getPluginManager().registerEvents(MentionPlugin.INSTANCE, new UserEvents());
        LOGGER.info("[MENTION-PLUGIN 2.2] successfully loaded!");
        Timer timer = new Timer();
        long minutes = TimeUnit.MINUTES.toMillis(Emulator.getConfig().getInt("mentionplugin.database.log_timeout_minutes", 30));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(MentionPlugin.getMessages() != null)
                DatabaseLogger.save(MentionPlugin.getMessages());
            }
        }, 0, minutes);
    }

    @EventHandler
    public static void onEmulatorStopped(EmulatorStartShutdownEvent event) {
        LOGGER.info("[MENTIONPLUGIN] Stopping...");
        DatabaseLogger.save(MentionPlugin.getMessages());
    }
}
