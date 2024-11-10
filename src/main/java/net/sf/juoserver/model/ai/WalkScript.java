package net.sf.juoserver.model.ai;

import net.sf.juoserver.api.AIScript;
import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.NpcContext;
import net.sf.juoserver.api.NpcSession;
import net.sf.juoserver.model.Position;

public class WalkScript implements AIScript {

    @Override
    public boolean isActive(NpcSession session) {
        return true;
    }

    @Override
    public void execute(NpcContext context, NpcSession session) {
        //session.move(Direction.North, false);
        var mobile = session.getMobile();
        var enemy = session.findMobilesInRange(false)
                .filter(mobile1 -> {
                    System.out.println(mobile1.getName());
                    return true;
                })
                .findFirst()
                .orElseThrow();
        session.moveTowards(enemy);
    }

}
