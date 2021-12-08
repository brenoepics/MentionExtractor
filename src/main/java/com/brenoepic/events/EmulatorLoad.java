package com.brenoepic.events;

import com.brenoepic.logging.DatabaseLogger;
import com.eu.habbo.Emulator;
import com.brenoepic.MentionPlugin;
import com.brenoepic.utils.Extras;
import com.eu.habbo.database.Database;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;

import java.util.Timer;
import java.util.TimerTask;


public class EmulatorLoad implements EventListener {
    @EventHandler
    public static void onEmulatorLoaded(EmulatorLoadedEvent event) {
        Extras.checkDatabase();
        Extras.loadTexts();
        Emulator.getPluginManager().registerEvents(MentionPlugin.INSTANCE, new UserEvents());
        MentionPlugin.LOGGER.info("[MENTION-PLUGIN 2.2] successfully loaded!");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(MentionPlugin.getMessages() != null)
                DatabaseLogger.save(MentionPlugin.getMessages());
            }
        }, 0, Emulator.getConfig().getInt("mentionplugin.database.save_timeout", 5000));
    }
}
