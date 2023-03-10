package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Message;

import java.io.IOException;

/**
 * Protocol input/output boundary port. This interface is used by the protocol
 * clients to handle protocol messages and by the protocol to update its clients
 * about events occurring in the protocol.
 */
public interface ProtocolIoPort {
	String getName();
	void handleClientMessage(Message msg) throws IOException;
	void sendToClient(Message... messages) throws IOException;
	void deactivate();
}
