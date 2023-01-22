package net.sf.juoserver.controller;

import net.sf.juoserver.api.CharacterStatus;
import net.sf.juoserver.protocol.CharacterDraw;
import net.sf.juoserver.protocol.WarMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.verify;

public class HandleWarModeTest extends AbstractNewGameControllerTest {

    @DisplayName("Toggle war mode should update session")
    @Test
    public void toggleWarModeShouldUpdateSession() {
        var messages = gameController.handle(new WarMode(CharacterStatus.WarMode));
        verify(session).toggleWarMode(true);
        var expected = List.of(new WarMode(CharacterStatus.WarMode), new CharacterDraw(session.getMobile()));
        assertIterableEquals(expected, messages);
    }

}
