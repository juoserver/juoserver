package net.sf.juoserver.controller;

import net.sf.juoserver.api.IntercomException;
import net.sf.juoserver.protocol.CharacterDraw;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class MobileChangedWarModeTest extends AbstractNewGameControllerTest {

    @DisplayName("Should update client")
    @Test
    public void shouldUpdateClient() throws IOException {
        gameController.mobileChangedWarMode(mobile);
        verify(clientHandler).sendToClient(new CharacterDraw(mobile));
    }

    @DisplayName("Should fail to update client")
    @Test
    public void shouldFailToUpdateClient() {
        assertThrows(IntercomException.class, ()->{
            doThrow(new IOException())
                    .when(clientHandler)
                    .sendToClient(new CharacterDraw(mobile));
            gameController.mobileChangedWarMode(mobile);
        });
    }
}
