package net.sf.juoserver.builder;

import net.sf.juoserver.api.*;
import net.sf.juoserver.configuration.PropertyFileBasedConfiguration;
import net.sf.juoserver.files.mondainslegacy.MondainsLegacyFileReadersFactory;
import net.sf.juoserver.model.InMemoryDataManager;
import net.sf.juoserver.model.UOCore;
import net.sf.juoserver.networking.threaded.ThreadedServerAdapter;
import net.sf.juoserver.protocol.ControllerFactory;
import net.sf.juoserver.protocol.UOProtocolMessageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class JUOServerBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(JUOServerBuilder.class);
    private Configuration configuration;
    private DataManager dataManager;
    private final Set<Command> commands = new HashSet<>();

    private JUOServerBuilder() {
    }

    public static JUOServerBuilder newInstance() {
        JUOServerBuilder builder = new JUOServerBuilder();
        builder.configuration = new PropertyFileBasedConfiguration();
        builder.dataManager = new InMemoryDataManager();
        return builder;
    }

    public JUOServerBuilder registerCommand(Command command) {
        this.commands.add(command);
        return this;
    }

    public JUOServer build() {
        Core core = new UOCore(new MondainsLegacyFileReadersFactory(), dataManager, configuration);
        Server server = new ThreadedServerAdapter(configuration, new ControllerFactory(core, configuration, commands));
        return new JUOServer() {

            @Override
            public void run() throws IOException {
                LOGGER.info("Initializing!!");
                core.init();
                server.acceptClientConnections();
            }
        };
    }

}
