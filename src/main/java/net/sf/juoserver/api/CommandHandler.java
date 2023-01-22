package net.sf.juoserver.api;

import net.sf.juoserver.protocol.ProtocolIoPort;
import net.sf.juoserver.protocol.UnicodeSpeechRequest;

public interface CommandHandler {

    boolean isCommand(UnicodeSpeechRequest request);
    void execute(ProtocolIoPort protocolIoPort, PlayerSession session, UnicodeSpeechRequest speechRequest);
}
