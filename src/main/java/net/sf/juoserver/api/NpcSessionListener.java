package net.sf.juoserver.api;

public interface NpcSessionListener {

    void onSessionCreated(NpcMobile mobile, NpcSession session);

    void onSessionClosed(NpcMobile mobile, NpcSession session);
}
