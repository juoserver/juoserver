package net.sf.juoserver.model.ai;

import net.sf.juoserver.api.AIScript;
import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.NpcSession;

public class WalkScript implements AIScript {

    @Override
    public boolean isActivated(NpcSession session) {
        return true;
    }

    @Override
    public void execute(NpcSession session) {
        session.move(Direction.North, false);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
