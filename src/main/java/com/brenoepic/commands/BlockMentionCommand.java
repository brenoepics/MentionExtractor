package com.brenoepic.commands;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;

public class BlockMentionCommand extends Command
{
    public BlockMentionCommand(final String permission, final String[] keys) {
        super(permission, keys);
    }
    
    public boolean handle(final GameClient gameClient, final String[] strings) throws Exception {
        return false;
        //To do (system using the cache, whether the user wants to receive mentions or not) I will do this later
    }
}

