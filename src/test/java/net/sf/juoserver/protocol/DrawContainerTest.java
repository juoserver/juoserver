package net.sf.juoserver.protocol;

import net.sf.juoserver.TestingFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawContainerTest {

    @Test
    public void shouldValidateEncodedContainer() {
        var item = TestingFactory.createTestContainer(1, 1);
        var buffer = new DrawContainer(item).encode().flip();
        assertEquals(DrawContainer.CODE, buffer.get());
        assertEquals(item.getSerialId(), buffer.getInt());
        assertEquals(item.getGumpId(), buffer.getShort());
        assertEquals((short)0x7D, buffer.getShort());
    }

}