
package com.brenoepic.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.commands.Command;

import java.util.Objects;

public class BlockMentionCommand extends Command
{
    public BlockMentionCommand(final String permission, final String[] keys) {
        super(permission, keys);
    }

    public boolean handle(final GameClient gameClient, final String[] strings){
        final boolean blockmention = (boolean) gameClient.getHabbo().getHabboStats().cache.get("blockmention");

        if (!blockmention) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.cmd_mention.success.off"));
            gameClient.getHabbo().getHabboStats().cache.put("blockmention", true);
        } else {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.cmd_mention.success.on"));
            gameClient.getHabbo().getHabboStats().cache.put("blockmention", false);
        }
        return true;
    }
}
