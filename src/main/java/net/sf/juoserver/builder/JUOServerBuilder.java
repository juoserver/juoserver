package net.sf.juoserver.builder;

import net.sf.juoserver.api.*;
import net.sf.juoserver.configuration.PropertyFileBasedConfiguration;
import net.sf.juoserver.files.mondainslegacy.MondainsLegacyFileReadersFactory;
import net.sf.juoserver.model.InMemoryDataManager;
import net.sf.juoserver.model.UOCore;
import net.sf.juoserver.networking.mina.MinaMultiplexingServerAdapter;
import net.sf.juoserver.networking.threaded.ThreadedServerAdapter;
import net.sf.juoserver.protocol.ControllerFactory;
import net.sf.juoserver.api.CombatSystem;
import net.sf.juoserver.protocol.combat.CombatSystemImpl;
import net.sf.juoserver.protocol.combat.PhysicalDamageCalculatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class JUOServerBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(JUOServerBuilder.class);
    private Configuration configuration;
    private DataManager dataManager;
    private final List<ServerModule> modules = new ArrayList<>();
    private ServerType serverType;
    private CombatSystem combatSystem;

    private JUOServerBuilder() {
    }

    public static JUOServerBuilder newInstance() {
        JUOServerBuilder builder = new JUOServerBuilder();
        builder.configuration = new PropertyFileBasedConfiguration();
        builder.dataManager = new InMemoryDataManager();
        builder.serverType = ServerType.THREADED;
        builder.combatSystem = new CombatSystemImpl(new PhysicalDamageCalculatorImpl());
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
        Core core = new UOCore(new MondainsLegacyFileReadersFactory(), dataManager, configuration);
        Server server = getServer(core);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        return () -> {
            LOGGER.info("Initializing!!");
            core.init();
            executorService.scheduleWithFixedDelay(()->{
                try {
                    combatSystem.execute();
                } catch (RuntimeException exception) {
                    LOGGER.error("Error executing CombatSystem", exception);
                }
            }, 500, 1000, TimeUnit.MILLISECONDS);
            server.acceptClientConnections();
        };
    }

    private Server getServer(Core core) {
        var commands = modules.stream()
                .map(ServerModule::getCommands)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        switch (serverType) {
            case THREADED:
                return new ThreadedServerAdapter(configuration, new ControllerFactory(core, configuration, commands, combatSystem));
            case MULTIPLEXING:
                return new MinaMultiplexingServerAdapter(configuration, new ControllerFactory(core, configuration, commands, combatSystem));
            default:
                throw new IllegalArgumentException(String.format("ServerType %s does not exist", serverType));
        }
    }
}
