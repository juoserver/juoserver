package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.model.UOItem;
import net.sf.juoserver.protocol.ObjectInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ItemCreatedTest extends AbstractNewGameControllerTest {

    @DisplayName("Should send object info")
    @Test
    public void shouldSendObjectInfo() throws IOException {
        var item = new UOItem(1, 1);
        gameController.itemCreated(item);
        verify(clientHandler).sendToClient(any(ObjectInfo.class));
    }

}
