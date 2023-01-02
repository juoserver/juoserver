package net.sf.juoserver.main;

import net.sf.juoserver.api.Command;
import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.api.Core;
import net.sf.juoserver.api.Server;
import net.sf.juoserver.configuration.PropertyFileBasedConfiguration;
import net.sf.juoserver.files.mondainslegacy.MondainsLegacyFileReadersFactory;
import net.sf.juoserver.model.InMemoryDataManager;
import net.sf.juoserver.model.UOCore;
import net.sf.juoserver.networking.mina.MinaMultiplexingServerAdapter;
import net.sf.juoserver.networking.threaded.ThreadedServerAdapter;
import net.sf.juoserver.protocol.ControllerFactory;
import net.sf.juoserver.protocol.UOProtocolMessageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static enum ServerType { THREADED, MULTIPLEXING };
	private final Configuration configuration;
	private final Core core;
	private final Set<Command> commands;
	private final Server server;


	private Main(ServerType serverType) {
		super();
		configuration = new PropertyFileBasedConfiguration();
		core = new UOCore(new MondainsLegacyFileReadersFactory(), new InMemoryDataManager(), configuration);
		commands = new HashSet<>();
		server = createServer(serverType, core, configuration, commands);
	}
	
	private void run() throws IOException {
		LOGGER.info("Initializing!!");
		core.init();
		server.acceptClientConnections();
	}
	
	private Server createServer(ServerType serverType, Core core, Configuration configuration, Set<Command> commands) {
		switch (serverType) {
		case MULTIPLEXING:
			return new MinaMultiplexingServerAdapter(configuration, new ControllerFactory(core, configuration, commands));
		case THREADED:
			return new ThreadedServerAdapter(configuration, new ControllerFactory(core, configuration, commands));
		default:
			throw new IllegalStateException(serverType.name());
		}
	}

	public static void main(String[] args) throws IOException {
		new Main(ServerType.THREADED).run();
	}
	
	private static ServerType parseServerType(String[] args) {
		return args.length == 0? ServerType.MULTIPLEXING : ServerType.valueOf(args[0].toUpperCase());
	}
}
