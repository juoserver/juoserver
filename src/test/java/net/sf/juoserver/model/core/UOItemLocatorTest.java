package net.sf.juoserver.model.core;

import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.Item;
import net.sf.juoserver.model.Position;
import net.sf.juoserver.model.UOItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UOItemLocatorTest {

    private UOItemLocator itemLocator;
    private Map<Integer, Item> itemsBySerialId;

    @BeforeEach
    public void setUp() {
        itemsBySerialId = new HashMap<>();
        itemLocator = new UOItemLocator(itemsBySerialId);
    }

    @Test
    public void shouldFindItemOnEastWhenInitialized() {
        itemsBySerialId.put(1, new UOItem(1, 1)
                .setLocation(110, 92, 0));
        itemsBySerialId.put(2, new UOItem(2, 2)
                .setLocation(110, 109, 0));
        itemLocator.init();

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.East, 10);
        assertIterableEquals(itemsBySerialId.values(), items.collect(Collectors.toList()));
    }

    @Test
    public void shouldFindItemsOnEstWhenLocationUpdated() {
        var item = new UOItem(1, 1);
        item.addPropertyChangeListener(itemLocator);
        item.setLocation(110, 92, 0);

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.East, 10);
        assertEquals(item, items.collect(Collectors.toList()).get(0));
    }

    @Test
    public void shouldFindItemsOnWestWhenInitialized() {
        itemsBySerialId.put(1, new UOItem(1, 1)
                .setLocation(90, 92, 0));
        itemsBySerialId.put(2, new UOItem(2, 2)
                .setLocation(90, 109, 0));
        itemLocator.init();

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.West, 10);
        assertIterableEquals(itemsBySerialId.values(), items.collect(Collectors.toList()));
    }

    @Test
    public void shouldFindItemsOnWestWhenLocationUpdated() {
        var item = new UOItem(1, 1);
        item.addPropertyChangeListener(itemLocator);
        item.setLocation(90, 92, 0);

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.West, 10);
        assertEquals(item, items.collect(Collectors.toList()).get(0));
    }

    @Test
    public void shouldFindItemsOnNorthWhenInitialized() {
        itemsBySerialId.put(1, new UOItem(1, 1)
                .setLocation(95, 90, 0));
        itemsBySerialId.put(2, new UOItem(2, 2)
                .setLocation(105, 90, 0));
        itemLocator.init();

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.North, 10);
        assertIterableEquals(itemsBySerialId.values(), items.collect(Collectors.toList()));
    }

    @Test
    public void shouldFindItemsOnNorthWhenLocationUpdated() {
        var item = new UOItem(1, 1);
        item.addPropertyChangeListener(itemLocator);
        item.setLocation(95, 90, 0);

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.North, 10);
        assertEquals(item, items.collect(Collectors.toList()).get(0));
    }

    @Test
    public void shouldFindItemsOnSouthWhenInitialized() {
        itemsBySerialId.put(1, new UOItem(1, 1)
                .setLocation(95, 110, 0));
        itemsBySerialId.put(2, new UOItem(2, 2)
                .setLocation(105, 110, 0));
        itemLocator.init();

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.South, 10);
        assertIterableEquals(itemsBySerialId.values(), items.collect(Collectors.toList()));
    }

    @Test
    public void shouldFindItemsOnSouthWhenLocationUpdated() {
        var item = new UOItem(1, 1);
        item.addPropertyChangeListener(itemLocator);
        item.setLocation(95, 110, 0);

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.South, 10);
        assertEquals(item, items.collect(Collectors.toList()).get(0));
    }

    @Test
    public void shouldFindItemsOnNorthEastWhenInitialized() {
        // Must be found on East
        itemsBySerialId.put(1, new UOItem(1, 1)
                .setLocation(110, 92, 0));
        itemsBySerialId.put(2, new UOItem(2, 2)
                .setLocation(110, 107, 0));

        // Must be found on North
        itemsBySerialId.put(3, new UOItem(3, 3)
                .setLocation(94, 90, 0));
        itemsBySerialId.put(4, new UOItem(4, 4)
                .setLocation(108, 90, 0));

        itemLocator.init();

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.Northeast, 10);
        assertIterableEquals(itemsBySerialId.values(), items.collect(Collectors.toList()));
    }
}