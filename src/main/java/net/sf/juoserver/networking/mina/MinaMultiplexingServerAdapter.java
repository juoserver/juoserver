package net.sf.juoserver.networking.mina;

import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.api.Server;
import net.sf.juoserver.protocol.ControllerFactory;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MinaMultiplexingServerAdapter implements Server {
	private static final Logger LOGGER = LoggerFactory.getLogger(MinaMultiplexingServerAdapter.class);
	private static final int BUF_SIZE = 1024;

	private final Configuration configuration;
	private final ControllerFactory controllerFactory;
	
	public MinaMultiplexingServerAdapter(Configuration configuration, ControllerFactory controllerFactory) {
		super();
		this.configuration = configuration;
		this.controllerFactory = controllerFactory;
	}
	
	@Override
	public void acceptClientConnections() throws IOException {
		LOGGER.info("Starting multiplexing server...");
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		if (configuration.isPacketLoggingEnabled()) {
			acceptor.getFilterChain().addLast("transport logger", new LoggingFilter());
		}
		acceptor.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new UOProtocolFactory()));
		if (configuration.isPacketLoggingEnabled()) {
			// Mina's LoggingFilter is bugged: https://issues.apache.org/jira/browse/DIRMINA-833
			acceptor.getFilterChain().addLast("packets logger", new UOProtocolLoggingFilter());
		}
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.getSessionConfig().setReadBufferSize(BUF_SIZE);
		acceptor.getSessionConfig().setReuseAddress(true);
		acceptor.setHandler(new UOIoHandler(acceptor, controllerFactory));
		acceptor.bind(new InetSocketAddress(configuration.getServer().getPort()));
		LOGGER.info("Listening on port " + configuration.getServer().getPort());
	}
}
