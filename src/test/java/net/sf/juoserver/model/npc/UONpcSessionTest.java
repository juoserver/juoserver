package net.sf.juoserver.model.npc;

import net.sf.juoserver.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UONpcSessionTest {

    @Mock
    private NpcMobile mobile;
    @Mock
    private InterClientNetwork network;
    @Mock
    private Core core;
    @Mock
    private NpcContext context;
    @InjectMocks
    private UONpcSession session;

    @BeforeEach
    public void setUp() {
        session.setContext(context);
    }

    @Test
    public void shouldFindMobilesInRange() {
        var result = Stream.of(mock(Mobile.class));
        when(core.findMobilesInRange(mobile))
                .thenReturn(result);
        assertEquals(result, session.findMobilesInRange(false));
    }

    @Test
    public void shouldMoveToDirection() {
        session.move(Direction.East, false);
        verify(mobile).setRunning(false);
        verify(mobile).setDirection(Direction.East);
        verify(mobile).moveForward();
        verify(network).notifyOtherMobileMovement(mobile);
    }

}