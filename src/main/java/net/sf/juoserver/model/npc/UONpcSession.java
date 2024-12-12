package net.sf.juoserver.model.npc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.sf.juoserver.api.*;

import java.util.stream.Stream;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
class UONpcSession implements ContextBasedNpcSession {

    @ToString.Include
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

    @Override
    public void receiveDamage(int damage) {
        mobile.setCurrentHitPoints( Math.max(mobile.getCurrentHitPoints() - damage, 0) );
        if (mobile.getCurrentHitPoints() == 0) {
            mobile.kill();
            network.notifyOtherKilled(mobile);
        } else {
            network.notifyOtherDamaged(mobile, damage);
        }
    }

    @Override
    public void attackWithDamage(Mobile attacked, int damage) {
        mobile.setCurrentHitPoints( Math.max(mobile.getCurrentHitPoints() - damage, 0) );
        network.notifyAttackWithDamage(mobile, damage, attacked);
    }
}
