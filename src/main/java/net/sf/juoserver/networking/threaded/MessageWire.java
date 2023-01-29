package net.sf.juoserver.networking.threaded;

import net.sf.juoserver.api.Message;

import java.io.IOException;
import java.util.List;

public interface MessageWire {
	void init() throws IOException;
	void shutDown() throws IOException;
	List<? extends Message> readMessages() throws IOException;
	void sendMessages(List<Message> replies) throws IOException;
}
