package net.sf.juoserver.api;

import java.util.stream.Stream;

public interface NpcSession extends CombatSession {

    Mobile getMobile();

    Stream<Mobile> findMobilesInRange(boolean includeNpc);

    void move(Direction direction, boolean running);

    void moveTowards(Point2D location);
}
