package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOAccount;
import net.sf.juoserver.protocol.CircularClientMovementTracker;
import net.sf.juoserver.protocol.GameController;
import net.sf.juoserver.protocol.ProtocolIoPort;
import net.sf.juoserver.protocol.item.ItemManager;
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
    @Mock PlayerSession session;
    @Mock Core core;
    @Mock Configuration configuration;
    @Mock LoginManager loginManager;
    @Mock InterClientNetwork intercom;
    @Mock ProtocolIoPort clientHandler;
    @Mock ItemManager itemManager;
    @Mock CommandManager commandManager;
    @Mock CombatSystem combatSystem;
    @Mock GeneralInfoManager generalInfoManager;
    protected GameController gameController;

    @BeforeEach
    public void configure() {
        gameController = new GameController("client", clientHandler, core, configuration, new CircularClientMovementTracker(), loginManager, intercom, itemManager, commandManager, combatSystem, generalInfoManager);
        gameController.setSession(session);

        lenient().when(session.getMobile())
                .thenReturn(mobile);
        lenient().when(core.findMobileByID(mobile.getSerialId()))
                .thenReturn(mobile);
    }
}
