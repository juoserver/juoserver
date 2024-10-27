package net.sf.juoserver.protocol;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerItemsTest {

    @Test
    public void shouldValidateContainerItemsEncode() {
        var container = TestingFactory.createTestContainer(2, 1);
        var item = TestingFactory.createTestItem(1,1);
        var position = new Position(11,22);
        container.addItem(item, position);
        var buffer = new ContainerItems(container).encode().flip();

        assertEquals(ContainerItems.CODE, buffer.get());
        assertEquals(25, buffer.getShort());
        assertEquals(1, buffer.getShort());
        // item
        assertEquals(item.getSerialId(), buffer.getInt());
        assertEquals(item.getModelId(), buffer.getShort());
        assertEquals(0, buffer.get());
        assertEquals(1, buffer.getShort());
        assertEquals(position.getX(), buffer.getShort());
        assertEquals(position.getY(), buffer.getShort());
        assertEquals(0, buffer.get());
        assertEquals(container.getSerialId(), buffer.getInt());
        assertEquals(item.getHue(), buffer.getShort());
    }
}