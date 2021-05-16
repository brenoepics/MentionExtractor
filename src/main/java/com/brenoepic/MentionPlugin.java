package com.brenoepic;

import com.brenoepic.events.EmulatorLoad;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


public class MentionPlugin extends HabboPlugin implements EventListener {
    public static final Logger LOGGER = LoggerFactory.getLogger(MentionPlugin.class);
    public static MentionPlugin INSTANCE = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Emulator.getPluginManager().registerEvents(this, new EmulatorLoad());
    }

    public void onDisable(){

    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    @EventHandler
    public void onEmulatorLoaded(EmulatorLoadedEvent event) throws SQLException {

   }
        public static void main(String[] args) {
        System.out.println("Don't run this separately!");
    }
}
