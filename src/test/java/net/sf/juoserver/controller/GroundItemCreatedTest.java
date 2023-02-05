package net.sf.juoserver.controller;

import net.sf.juoserver.model.UOItem;
import net.sf.juoserver.protocol.ObjectInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class GroundItemCreatedTest extends AbstractNewGameControllerTest {

    @DisplayName("Should send object info")
    @Test
    public void shouldSendObjectInfo() throws IOException {
        var item = new UOItem(1, 1);
        gameController.groundItemsCreated(Collections.singletonList(item));
        verify(clientHandler).sendToClient(any(ObjectInfo.class));
    }

}
