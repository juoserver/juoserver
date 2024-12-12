package net.sf.juoserver.protocol;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOLoginManager;
import net.sf.juoserver.protocol.generalinfo.GeneralInfoManagerImpl;
import net.sf.juoserver.protocol.item.ItemManager;

import java.util.List;

public final class ControllerFactory {
	private final Core core;
	private final Configuration configuration;
	private final LoginManager loginManager;
	private final InterClientNetwork network;
	private final List<Command> commands;
	private final CombatSystem combatSystem;
	private final NpcSystem npcSystem;
	
	public ControllerFactory(Core core, Configuration configuration, List<Command> commands, CombatSystem combatSystem, InterClientNetwork network, NpcSystem npcSystem) {
		super();
		this.core = core;
		this.configuration = configuration;
		this.loginManager = new UOLoginManager(core);
		this.network = network;
		this.commands = commands;
		this.combatSystem = combatSystem;
		this.npcSystem = npcSystem;
	}

	public ProtocolController createGameController(ProtocolIoPort clientHandler) {
		var itemManager = new ItemManager();
		var generalInfoManager = new GeneralInfoManagerImpl(core);
		var commandManager = new CommandManagerImpl(commands, configuration);

		return new GameController(clientHandler.getName(), clientHandler, core, configuration, new CircularClientMovementTracker(),
				loginManager, network, npcSystem, itemManager, commandManager, combatSystem, generalInfoManager);
	}
	
	public ProtocolController createAuthenticationController(ProtocolIoPort clientHandler) {
		return new AuthenticationController(clientHandler, configuration, loginManager);
	}
}
