package com.brenoepic.events;

import com.eu.habbo.Emulator;
import com.brenoepic.MentionPlugin;
import com.brenoepic.utils.Extras;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;


public class EmulatorLoad implements EventListener {
    @EventHandler
    public static void onEmulatorLoaded(EmulatorLoadedEvent event) {
        Extras.checkDatabase();
        Extras.loadTexts();
        Emulator.getPluginManager().registerEvents(MentionPlugin.INSTANCE, new UserEvents());
        MentionPlugin.LOGGER.info("[MENTION-PLUGIN 2.2] successfully loaded!");
    }
}
