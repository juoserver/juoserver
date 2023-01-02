package net.sf.juoserver.networking.mina;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UOProtocolLoggingFilter extends IoFilterAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(UOProtocolLoggingFilter.class);

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		LOGGER.info("Received message: " + message);
		nextFilter.messageReceived(session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		LOGGER.info("Sending message: " + writeRequest.getMessage());
		nextFilter.messageSent(session, writeRequest);
	}
}
