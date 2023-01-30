package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.IntercomException;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.protocol.AttackSucceed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class MobileAttackFinishedTest extends AbstractNewGameControllerTest {

    private Mobile attacker = TestingFactory.createTestMobile(50, "loller");

    @Test
    public void shouldNotifyAttackSucceed() throws IOException {
        gameController.mobileAttackFinished(attacker);
        verify(clientHandler).sendToClient(new AttackSucceed(0));
    }

    @Test
    @DisplayName("Should fail to attack succeed")
    public void shouldFailToAttackSucceed() {
        assertThrows(IntercomException.class, ()->{
            doThrow(new IOException())
                            .when(clientHandler)
                            .sendToClient(any());
            gameController.mobileAttackFinished(attacker);
        });
    }
}
