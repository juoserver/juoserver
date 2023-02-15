package net.sf.juoserver.protocol;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.Intercom;
import net.sf.juoserver.model.UOLoginManager;

import java.util.Collection;
import java.util.List;

public final class ControllerFactory {
	private final Core core;
	private final Configuration configuration;
	private final LoginManager loginManager;
	private final InterClientNetwork network;
	private final List<Command> commands;
	private final CombatSystem combatSystem;
	
	public ControllerFactory(Core core, Configuration configuration, List<Command> commands, CombatSystem combatSystem) {
		super();
		this.core = core;
		this.configuration = configuration;
		this.loginManager = new UOLoginManager(core);
		this.network = new Intercom();
		this.commands = commands;
		this.combatSystem = combatSystem;
	}

	public ProtocolController createGameController(ProtocolIoPort clientHandler) {
		return new GameController(clientHandler.getName(), clientHandler, core, configuration, new CircularClientMovementTracker(),
				loginManager, network, commands, combatSystem);
	}
	
	public ProtocolController createAuthenticationController(ProtocolIoPort clientHandler) {
		return new AuthenticationController(clientHandler, configuration, loginManager);
	}
}
