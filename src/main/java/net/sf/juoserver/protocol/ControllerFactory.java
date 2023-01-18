package net.sf.juoserver.protocol;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.Intercom;
import net.sf.juoserver.model.UOLoginManager;
import net.sf.juoserver.protocol.combat.CombatSystem;
import net.sf.juoserver.protocol.combat.CombatSystemImpl;

import java.util.Collection;
import java.util.Set;

public final class ControllerFactory {
	private final Core core;
	private final Configuration configuration;
	private final LoginManager loginManager;
	private final InterClientNetwork network;
	private final CommandHandler commandManager;
	private final CombatSystem combatSystem;
	
	public ControllerFactory(Core core, Configuration configuration, Collection<Command> commands) {
		super();
		this.core = core;
		this.configuration = configuration;
		this.loginManager = new UOLoginManager(core);
		this.network = new Intercom();
		this.commandManager = new CommandHandlerImpl(this.core, this.network, commands, configuration);
		this.combatSystem = new CombatSystemImpl();
	}

	public ProtocolController createGameController(ProtocolIoPort clientHandler) {
		return new GameController(clientHandler.getName(), clientHandler, core, new CircularClientMovementTracker(),
				loginManager, network, commandManager, combatSystem);
	}
	
	public ProtocolController createAuthenticationController(ProtocolIoPort clientHandler) {
		return new AuthenticationController(clientHandler, configuration, loginManager);
	}
}
