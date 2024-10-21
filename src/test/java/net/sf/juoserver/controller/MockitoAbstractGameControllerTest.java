package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOAccount;
import net.sf.juoserver.protocol.CircularClientMovementTracker;
import net.sf.juoserver.protocol.CommandManagerImpl;
import net.sf.juoserver.protocol.GameController;
import net.sf.juoserver.protocol.ProtocolIoPort;
import net.sf.juoserver.protocol.item.ItemManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class MockitoAbstractGameControllerTest {

    protected final Mobile mobile = TestingFactory.createTestMobile(42, "asder");
    protected final Account account = UOAccount.createAccount(0, "user", "psw", mobile.getSerialId());

    @Mock
    protected PlayerSession session;

    @Mock
    protected Core core;
    @Mock
    protected LoginManager loginManager;
    @Mock
    protected InterClientNetwork intercom;
    @Mock
    protected ProtocolIoPort clientHandler;
    @Mock
    protected CommandManager commandManager;
    @Mock
    protected CombatSystem combatSystem;

    protected GameController gameController;

    @BeforeEach
    public final void createComponents() throws IOException {
        this.gameController = new GameController("client", clientHandler, core, null, new CircularClientMovementTracker(),  loginManager, intercom, new ItemManager(), commandManager, combatSystem, null);
        gameController.setSession(session);

        lenient().when(session.getMobile())
                        .thenReturn(mobile);
        lenient().when(core.findMobileByID(mobile.getSerialId()))
                        .thenReturn(mobile);
    }
}
