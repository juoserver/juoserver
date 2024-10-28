package net.sf.juoserver.model;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.Account;
import net.sf.juoserver.api.Core;
import net.sf.juoserver.api.InterClientNetwork;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.files.mondainslegacy.MondainsLegacyMapTile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UOPlayerSessionMockitoTest {

    @Mock
    private Core core;
    @Mock
    private ModelOutputPort listener;
    @Mock
    private InterClientNetwork network;
    @Spy
    private final Account account = new UOAccount(41, "asder", "asder");
    private final Mobile mobile = TestingFactory.createTestMobile(100, "asder", new PointInSpace(100,100,0));
    @InjectMocks
    private UOPlayerSession session;

    @BeforeEach
    public void setUp() {
        lenient()
            .when(core.findMobileByID(mobile.getSerialId()))
            .thenReturn(mobile);
        lenient()
                .when(core.getTile(anyInt(), anyInt()))
                .thenReturn(new MondainsLegacyMapTile(0, 42));
        account.addMobileSerialId(mobile.getSerialId());
        session.selectCharacterById(0);
    }

    @Test
    public void whenOtherMobileMovementEnteredOnRange() {
        var moving = TestingFactory.createTestMobile(101, "loller", new PointInSpace(100,100,0));

        session.onOtherMobileMovement(moving);

        verify(listener).mobileApproached(moving);
        verify(network).notifyEnteredRange(mobile, moving);
        verify(listener).mobileChanged(moving);

        assertTrue(session.getMobilesInRange().contains(moving));
    }

    @Test
    public void whenOtherMobileMovementOutOfSightWasNotInRange() {
        var moving = TestingFactory.createTestMobile(101, "loller", new PointInSpace(125,100,0));

        session.onOtherMobileMovement(moving);

        verify(listener, never()).mobileChanged(moving);
    }

    @Test
    public void whenOtherMobileMovementOutOfSightWasInRange() {
        var moving = TestingFactory.createTestMobile(101, "loller", new PointInSpace(125,100,0));
        session.getMobilesInRange().add(moving);

        session.onOtherMobileMovement(moving);

        verify(listener).mobileGotAway(moving);
        verify(network).notifyOutOfRange(mobile, moving);
        verify(listener).mobileChanged(moving);

        Assertions.assertFalse(session.getMobilesInRange().contains(moving));
    }
}
