package net.sf.juoserver.api;

import net.sf.juoserver.protocol.UnicodeSpeechRequest;

public interface CommandHandler {

    boolean isCommand(UnicodeSpeechRequest request);
    void execute(PlayerSession session, UnicodeSpeechRequest speechRequest);
}
