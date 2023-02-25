package net.sf.juoserver.model;

import net.sf.juoserver.api.Core;
import net.sf.juoserver.api.PlayerContext;
import net.sf.juoserver.api.PlayerSession;
import net.sf.juoserver.protocol.ProtocolIoPort;

public class UOPlayerContext implements PlayerContext {

    private final PlayerSession session;
    private final Core core;
    private final ProtocolIoPort protocolIoPort;

    public UOPlayerContext(PlayerSession session, Core core, ProtocolIoPort protocolIoPort) {
        this.session = session;
        this.core = core;
        this.protocolIoPort = protocolIoPort;
    }

    @Override
    public PlayerSession session() {
        return session;
    }

    @Override
    public Core core() {
        return core;
    }

    @Override
    public ProtocolIoPort protocolIoPort() {
        return protocolIoPort;
    }
}
