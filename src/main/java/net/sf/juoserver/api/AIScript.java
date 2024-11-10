package net.sf.juoserver.api;

public interface AIScript {

    boolean isActive(NpcSession session);
    void execute(NpcContext context, NpcSession session);

}
