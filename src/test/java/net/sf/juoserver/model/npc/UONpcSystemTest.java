package net.sf.juoserver.model.npc;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UONpcSystemTest {

    @Mock
    private Core core;
    @Mock
    private InterClientNetwork network;
    @Mock
    private Configuration configuration;
    @Mock
    private NpcSessionCycle npcSessionCycle;
    @InjectMocks
    private UONpcSystem uoNpcSystem;

    @Test
    public void teste() {
        var aiScript = Mockito.mock(AIScript.class);
        when(aiScript.isActive(any(NpcSession.class))).thenReturn(true);

        var mobile = TestingFactory.createTestNpcMobile(1, aiScript);
        uoNpcSystem.onMobileCreated(mobile);

        uoNpcSystem.execute(111l);

        verify(aiScript).isActive(any());
        verify(npcSessionCycle).execute(eq(aiScript), any(ContextBasedNpcSession.class));
    }

}