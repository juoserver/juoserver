package net.sf.juoserver.builder;

import net.sf.juoserver.api.*;
import net.sf.juoserver.configuration.ConfigurationFactory;
import net.sf.juoserver.files.mondainslegacy.MondainsLegacyFileReadersFactory;
import net.sf.juoserver.model.InMemoryDataManager;
import net.sf.juoserver.model.Intercom;
import net.sf.juoserver.model.UOConcurrentManagerExecutor;
import net.sf.juoserver.model.npc.DefaultNpcSystem;
import net.sf.juoserver.model.core.UOCore;
import net.sf.juoserver.networking.mina.MinaMultiplexingServerAdapter;
import net.sf.juoserver.networking.threaded.ThreadedServerAdapter;
import net.sf.juoserver.protocol.ControllerFactory;
import net.sf.juoserver.protocol.combat.CombatSystemImpl;
import net.sf.juoserver.protocol.combat.PhysicalDamageCalculatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.sf.juoserver.model.UOConcurrentManagerExecutor.Task.from;

public final class JUOServerBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(JUOServerBuilder.class);
    private Configuration configuration;
    private DataManager dataManager;
    private final List<ServerModule> modules = new ArrayList<>();
    private ServerType serverType;

    private JUOServerBuilder() {
    }

    public static JUOServerBuilder newInstance() {
        JUOServerBuilder builder = new JUOServerBuilder();
        builder.configuration = ConfigurationFactory.newInstance().newConfiguration();
        builder.dataManager = new InMemoryDataManager();
        builder.serverType = ServerType.THREADED;
        return builder;
    }

    public JUOServerBuilder registerModule(ServerModule module) {
        this.modules.add(Objects.requireNonNull(module, String.format("Module %s is null", module)));
        return this;
    }

    public JUOServerBuilder serverType(ServerType serverType) {
        this.serverType = Objects.requireNonNull(serverType, "ServerType is null");
        return this;
    }

    public JUOServer build() {
        LOGGER.info("Creating server managers");
        var commands = modules.stream()
                .map(ServerModule::getCommands)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        var core = new UOCore(new MondainsLegacyFileReadersFactory(), dataManager, configuration);
        var combatSystem = new CombatSystemImpl(new PhysicalDamageCalculatorImpl(configuration));
        var network = new Intercom();
        var npcSystem = new DefaultNpcSystem(core, network, configuration);

        var server = getServer(new ControllerFactory(core, configuration, commands, combatSystem, network, npcSystem));
        var executorService = new UOConcurrentManagerExecutor(from(combatSystem, 500), from(npcSystem, 1));
        LOGGER.info("Server managers successfully created");

        return () -> {
            LOGGER.info("Initializing!!");
            core.init();
            executorService.start();
            server.acceptClientConnections();
        };
    }

    private Server getServer(ControllerFactory controllerFactory) {
        switch (serverType) {
            case THREADED:
                return new ThreadedServerAdapter(configuration, controllerFactory);
            case MULTIPLEXING:
                return new MinaMultiplexingServerAdapter(configuration, controllerFactory);
            default:
                throw new IllegalArgumentException(String.format("ServerType %s does not exist", serverType));
        }
    }
}
