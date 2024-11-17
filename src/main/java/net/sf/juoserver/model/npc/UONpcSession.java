package net.sf.juoserver.model.npc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.sf.juoserver.api.*;

import java.util.stream.Stream;

@Getter
@Setter
@RequiredArgsConstructor
class UONpcSession implements ContextBasedNpcSession {

    private final NpcMobile mobile;
    private final InterClientNetwork network;
    private final Core core;
    private NpcContext context;

    @Override
    public Stream<Mobile> findMobilesInRange(boolean includeNpc) {
        return core.findMobilesInRange(mobile);
    }

    @Override
    public void move(Direction direction, boolean running) {
        mobile.setDirection(direction);
        mobile.setRunning(running);
        mobile.moveForward();

        network.notifyOtherMobileMovement(mobile);
    }

    @Override
    public void moveTowards(Point2D location) {
        var direction = NextStepPathfinding.findNextStep(this.mobile, location);
        move(direction,false);
    }
}
