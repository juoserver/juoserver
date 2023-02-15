package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOAccount;
import net.sf.juoserver.protocol.CircularClientMovementTracker;
import net.sf.juoserver.protocol.GameController;
import net.sf.juoserver.protocol.ProtocolIoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AbstractNewGameControllerTest {
    protected Mobile mobile = TestingFactory.createTestMobile(42, "asder");
    protected Account account = UOAccount.createAccount(0, "user", "psw", mobile.getSerialId());
    @Mock
    protected PlayerSession session;
    @Mock
    protected Core core;
    @Mock
    protected Configuration configuration;
    @Mock
    protected LoginManager loginManager;
    @Mock
    protected InterClientNetwork intercom;
    @Mock
    protected ProtocolIoPort clientHandler;
    @Mock
    protected CombatSystem combatSystem;
    protected GameController gameController;

    @BeforeEach
    public void configure() {
        gameController = new GameController("client", clientHandler, core, configuration, new CircularClientMovementTracker(), loginManager, intercom, Collections.emptyList(), combatSystem);
        gameController.setSession(session);

        lenient().when(session.getMobile())
                .thenReturn(mobile);
        lenient().when(core.findMobileByID(mobile.getSerialId()))
                .thenReturn(mobile);
    }
}
