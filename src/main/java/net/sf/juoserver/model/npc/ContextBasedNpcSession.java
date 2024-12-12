package net.sf.juoserver.model.npc;

import net.sf.juoserver.api.NpcContext;
import net.sf.juoserver.api.NpcMobile;
import net.sf.juoserver.api.NpcSession;

public interface ContextBasedNpcSession extends NpcSession {

    NpcContext getContext();

    void setContext(NpcContext context);

    NpcMobile getMobile();

}
