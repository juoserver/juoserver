package net.sf.juoserver.protocol;

import net.sf.juoserver.api.DeathAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeathScreenTest {

    @Test
    @DisplayName("Should validatte DeathScreen buffer content ")
    public void shouldValidateDeathScreenBufferContent() {
        var buffer = new DeathScreen(DeathAction.GHOST).encode().flip();
        assertEquals((byte)DeathScreen.CODE, buffer.get());
        assertEquals((byte)DeathAction.GHOST.getCode(), buffer.get());
        assertFalse(buffer.hasRemaining());
    }

}