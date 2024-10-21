package net.sf.juoserver.protocol;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.Mobile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeathAnimationTest {

    private Mobile mobile;

    @BeforeEach
    public void setUp() {
        this.mobile = TestingFactory.createTestMobile(1, "Asder");
    }

    @Test
    @DisplayName("Should validate DeathAnimation buffer")
    public void shouldValidateDeathAnimationBuffer() {
        var corpseId = 222;
        var buffer = new DeathAnimation(mobile, corpseId).encode().flip();
        assertEquals((byte)DeathAnimation.CODE, buffer.get());
        assertEquals(mobile.getSerialId(), buffer.getInt());
        assertEquals(corpseId, buffer.getInt());
        assertEquals(0, buffer.getInt());
        assertFalse(buffer.hasRemaining());
    }

}