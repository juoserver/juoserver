package net.sf.juoserver.model.npc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.InterClientNetwork;
import net.sf.juoserver.api.Npc;
import net.sf.juoserver.api.NpcSession;

@Getter
@Setter
@RequiredArgsConstructor
class UONpcSession implements NpcSession {

    private final Npc npc;
    private final InterClientNetwork network;
    private boolean active;

    @Override
    public void move(Direction direction, boolean running) {
        npc.setDirection(direction);
        npc.setRunning(false);
        npc.moveForward();

        network.notifyOtherMobileMovement(npc);
    }
}
