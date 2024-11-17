package net.sf.juoserver.api;

import net.sf.juoserver.model.npc.ContextBasedNpcSession;

public interface NpcSessionCycle {

    void execute(AIScript script, ContextBasedNpcSession session);

}
