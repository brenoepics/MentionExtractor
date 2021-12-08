package com.brenoepic;

import com.brenoepic.events.EmulatorLoad;
import com.brenoepic.logging.Message;
import com.brenoepic.timeout.MentionTimeout;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class MentionPlugin extends HabboPlugin implements EventListener {
    public static final Logger LOGGER = LoggerFactory.getLogger(MentionPlugin.class);
    public static MentionPlugin INSTANCE = null;
    public static MentionTimeout timeout;
    private static List<Message> messages;
    @Override
    public void onEnable() {
        INSTANCE = this;
        Emulator.getPluginManager().registerEvents(this, new EmulatorLoad());
        timeout = new MentionTimeout();
        messages = new ArrayList<>();
    }

    public void onDisable(){

    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    public static MentionTimeout getTimeout(){
        return timeout;
    }

    public static List<Message> getMessages(){
        return messages;
    }

    public static void addMessage(Message message){
        messages.add(message);
    }

    public static void dispose(){
        messages.clear();
    }
        public static void main(String[] args) {
        System.out.println("Don't run this separately!");
    }
}
