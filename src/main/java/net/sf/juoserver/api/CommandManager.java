package net.sf.juoserver.api;

import net.sf.juoserver.protocol.UnicodeSpeechRequest;

public interface CommandManager {

    boolean isCommand(UnicodeSpeechRequest request);
    void execute(UnicodeSpeechRequest speechRequest);
}
