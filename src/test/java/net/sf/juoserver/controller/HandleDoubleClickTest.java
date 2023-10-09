package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.protocol.DoubleClick;
import net.sf.juoserver.protocol.Paperdoll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

public class HandleDoubleClickTest extends AbstractNewGameControllerTest {

    @DisplayName("Should open paperdoll when mobile double clicked")
    @Test
    public void shouldOpenPaperdollWhenMobileDoubleClicked() {
        var serialId = 1;
        var doubleClick = new DoubleClick(serialId, true);
        var packetList = gameController.handle(doubleClick);

        var expected = new Paperdoll(serialId, String.format("%s, %s", mobile.getName(), mobile.getTitle()), false, false);
        assertIterableEquals(List.of(expected), packetList);
    }

    @DisplayName("Should use item when double clicked")
    @Test
    public void shouldUseItemWhenDoubleClicked() {
        var serialId = 1;

        var item = TestingFactory.createTestItem(serialId, 0x0089);
        when(core.findItemByID(serialId))
                .thenReturn(item);

        var itemUsedPackets = List.of(TestingFactory.createTestMessage(2, 0x0001));
        when(itemManager.use(item))
                .thenReturn(itemUsedPackets);

        var doubleClick = new DoubleClick(serialId, false);
        var packetList = gameController.handle(doubleClick);

        assertIterableEquals(itemUsedPackets, packetList);
    }

    @DisplayName("Should not find serial id")
    @Test
    public void shouldNotFindSerialId() {
        var serialId = 2;
        var doubleClick = new DoubleClick(serialId, false);
        assertIterableEquals(Collections.emptyList(), gameController.handle(doubleClick));
    }
}
