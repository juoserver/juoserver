package net.sf.juoserver.networking.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

@Slf4j
public class UOProtocolLoggingFilter extends IoFilterAdapter {

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		log.info("Received message: " + message);
		nextFilter.messageReceived(session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		log.info("Sending message: " + writeRequest.getMessage());
		nextFilter.messageSent(session, writeRequest);
	}
}
