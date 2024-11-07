package net.sf.juoserver.api;

public interface AIScript {

    boolean isActivated(NpcSession session);
    void execute(NpcSession session);

}
