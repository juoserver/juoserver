package net.sf.juoserver.controller;

import net.sf.juoserver.api.AnimationDirection;
import net.sf.juoserver.api.AnimationRepeat;
import net.sf.juoserver.api.AnimationType;
import net.sf.juoserver.api.IntercomException;
import net.sf.juoserver.protocol.CharacterAnimation;
import net.sf.juoserver.protocol.StatusBarInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class MobileDamagedTest extends AbstractNewGameControllerTest{
    @DisplayName("Should notify attack succeed")
    @Test
    public void shouldNotifyAttackSucceed() throws IOException {
        gameController.mobileDamaged(mobile, 1);
        verify(clientHandler).sendToClient(new StatusBarInfo(mobile), new CharacterAnimation(mobile, AnimationRepeat.ONCE, AnimationType.GET_HIT, 10, AnimationDirection.BACKWARD));
    }

    @Test
    @DisplayName("Should fail to attack succeed")
    public void shouldFailToAttackSucceed() {
        assertThrows(IntercomException.class, ()->{
            doThrow(new IOException())
                    .when(clientHandler)
                    .sendToClient(any());
            gameController.mobileDamaged(mobile, 1);
        });
    }
}
