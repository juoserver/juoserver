package net.sf.juoserver.api;

import net.sf.juoserver.protocol.ProtocolIoPort;

public interface PlayerContext {
    PlayerSession session();

    Core core();

    ProtocolIoPort protocolIoPort();
}
