package net.sf.juoserver.main.test;

import net.sf.juoserver.api.PlayerContext;
import net.sf.juoserver.protocol.AbstractCommand;
import net.sf.juoserver.protocol.CharacterDraw;
import net.sf.juoserver.protocol.DrawGamePlayer;

import java.io.IOException;
import java.util.List;

public class Move extends AbstractCommand {

    public Move() {
        super("go");
    }

    @Override
    public void execute(List<String> source, PlayerContext context) {
        var mobile = context.session().getMobile();

        mobile.location(6050,1575, 0);
        try {
            context.protocolIoPort().sendToClient(new DrawGamePlayer(mobile), new CharacterDraw(mobile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
