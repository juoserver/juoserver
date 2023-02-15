package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOAccount;
import net.sf.juoserver.protocol.CircularClientMovementTracker;
import net.sf.juoserver.protocol.GameController;
import net.sf.juoserver.protocol.ProtocolIoPort;
import net.sf.juoserver.protocol.combat.CombatSystemImpl;
import net.sf.juoserver.protocol.combat.PhysicalDamageCalculatorImpl;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;
import java.util.Collections;

public class AbstractGameControllerTest {
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
	
	protected final Mobile mobile = TestingFactory.createTestMobile(42, "asder");
	protected final Account account = UOAccount.createAccount(0, "user", "psw", mobile.getSerialId());
	protected final PlayerSession session = context.mock(PlayerSession.class);
	
	protected final Core core = context.mock(Core.class);
	protected final LoginManager loginManager = context.mock(LoginManager.class);
	protected final InterClientNetwork intercom = context.mock(InterClientNetwork.class);
	protected final ProtocolIoPort clientHandler = context.mock(ProtocolIoPort.class);
	protected final CommandManager commandManager = context.mock(CommandManager.class);
	protected final GameController gameController = 
			new GameController("client", clientHandler, core, null, new CircularClientMovementTracker(),  loginManager, intercom, Collections.emptyList(), new CombatSystemImpl(new PhysicalDamageCalculatorImpl(null)));
	
	@Before
	public final void createComponents() throws IOException {
		gameController.setSession(session);
		
		context.checking(new Expectations() {{
			allowing(session).getMobile();
				will(returnValue(mobile));
			allowing(core).findMobileByID(mobile.getSerialId());
				will(returnValue(mobile));
		}});
	}
}
