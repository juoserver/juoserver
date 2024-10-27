package net.sf.juoserver.protocol;

import net.sf.juoserver.TestingFactory;

import static org.junit.jupiter.api.Assertions.*;

class ContainerItemsTest {
    public void teste() {
        var container = TestingFactory.createTestContainer(2, 1);
        var buffer = new ContainerItems(container).encode().flip();
    }
}