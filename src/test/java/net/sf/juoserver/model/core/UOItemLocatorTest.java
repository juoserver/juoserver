package net.sf.juoserver.model.core;

import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.Point2D;
import net.sf.juoserver.model.Position;
import net.sf.juoserver.model.UOItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UOItemLocatorTest {

    private AtomicInteger serialSequence;
    private UOItemLocator itemLocator;
    private Map<Integer, Item> itemsBySerialId;

    @BeforeEach
    public void setUp() {
        serialSequence = new AtomicInteger(1);
        itemsBySerialId = new HashMap<>();
        itemLocator = new UOItemLocator(itemsBySerialId);
    }

    @DisplayName("Should find items on east when initialized")
    @Test
    public void shouldFindItemOnEastWhenInitialized() {
        var distance = 15;
        var position = new Position(150,208);
        var items = givenItemsOnEast(position, distance);

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.East, distance)
                .sorted()
                .collect(Collectors.toList());

        assertIterableEquals(items, itemsFound);
    }

    @Test
    public void shouldFindItemsOnNorthWhenInitialized() {
        int distance = 10;
        var position = new Position(150,143);
        var items = givenItemsOnNorth(position, distance);

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.North, distance)
                .collect(Collectors.toList());
        assertTrue(items.size() == itemsFound.size() && items.containsAll(itemsFound));
    }

    @Test
    public void shouldFindItemsOnWestWhenInitialized() {
        var distance = 10;
        var position = new Position(100, 100);
        var items = givenItemsOnWest(position, distance);

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.West, distance)
                .collect(Collectors.toList());

        assertTrue(items.size() == itemsFound.size() && items.containsAll(itemsFound));
    }

    @DisplayName("Should find items on south when initialized")
    @Test
    public void shouldFindItemsOnSouthWhenInitialized() {
        var distance = 20;
        var position = new Position(50, 78);
        var items = givenItemsOnSouth(position, distance);

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.South, distance)
                .sorted()
                .collect(Collectors.toList());
        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on southwest when locator initialized")
    @Test
    public void shouldFindItemsOnSouthwestWhenInitialized() {
        int distance = 20;
        var position = new Position(564,300);

        var items = new LinkedList<Item>() {{
            addAll(givenItemsOnWest(position, distance));
            addAll(givenItemsOnSouth(position, distance));
        }};

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.Southwest, distance)
                .sorted()
                .collect(Collectors.toList());

        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on northwest when initialized")
    @Test
    public void shouldFindItemsOnNorthEastWhenInitialized() {
        int distance = 10;
        var position = new Position(200,209);

        var items = new LinkedList<Item>() {{
            addAll(givenItemsOnEast(position, distance));
            addAll(givenItemsOnNorth(position, distance));
        }};

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.Northeast, distance)
                .sorted()
                .collect(Collectors.toList());

        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on northwest when initialized")
    @Test
    public void shouldFindItemsOnNorthWestWhenInitialized() {
        int distance = 2;
        var position = new Position(10,20);

        var items = new LinkedList<Item>() {{
            addAll(givenItemsOnWest(position, distance));
            addAll(givenItemsOnNorth(position, distance));
        }};

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.Northwest, distance)
                .sorted()
                .collect(Collectors.toList());

        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on Southeast where initialized")
    @Test
    public void shouldFindItemsOnSouthEastWhenInitialized() {
        int distance = 10;
        var position = new Position(1500,125);

        var items = new LinkedList<Item>() {{
            addAll(givenItemsOnEast(position, distance));
            addAll(givenItemsOnSouth(position, distance));
        }};

        itemLocator.init();

        var itemsFound = itemLocator.findItemsByDirection(position, Direction.Southeast, distance)
                .sorted()
                .collect(Collectors.toList());

        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on east when location updated")
    @Test
    public void shouldFindItemsOnEstWhenLocationUpdated() {
        var item = new UOItem(1, 1);
        item.addPropertyChangeListener(itemLocator);
        item.location(110, 92, 0);

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.East, 10);
        assertEquals(item, items.collect(Collectors.toList()).get(0));
    }

    @DisplayName("Should find items on west when location updated")
    @Test
    public void shouldFindItemsOnWestWhenLocationUpdated() {
        var item = new UOItem(1, 1);
        item.addPropertyChangeListener(itemLocator);
        item.location(90, 92, 0);

        var items = itemLocator.findItemsByDirection(new Position(100, 100), Direction.West, 10);
        assertEquals(item, items.collect(Collectors.toList()).get(0));
    }

    @DisplayName("Should find items on north when location updated")
    @Test
    public void shouldFindItemsOnNorthWhenLocationUpdated() {
        int distance = 10;
        var items = List.of(
            givenItemWithListener(95,90),
            givenItemWithListener(106,90)
        );

        var itemsFound = itemLocator.findItemsByDirection(new Position(100, 100), Direction.North, distance)
                .collect(Collectors.toList());
        assertEquals(items, itemsFound);
    }

    @DisplayName("Should find items on south when location updated")
    @Test
    public void shouldFindItemsOnSouthWhenLocationUpdated() {
        var items = Stream.of(
            givenItemWithListener(95,110),
            givenItemWithListener(106,110)
        ).sorted().collect(Collectors.toList());

        var itemsFound = itemLocator.findItemsByDirection(new Position(100, 100), Direction.South, 10)
                .sorted()
                .collect(Collectors.toList());
        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on Northeast when location updated")
    @Test
    public void shouldFindItemsOnNorthEastWhenLocationUpdated() {
        int distance = 10;
        var items = Stream.of(
            // Must be found on east
            givenItemWithListener(110,106),
            givenItemWithListener(110,92),

            // Must be found on north
            givenItemWithListener(93,90),
            givenItemWithListener(108,90)
        ).sorted().collect(Collectors.toList());

        var itemsFound = itemLocator.findItemsByDirection(new Position(100, 100), Direction.Northeast, distance)
                .sorted()
                .collect(Collectors.toList());
        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on Northwest when location updated")
    @Test
    public void shouldFindItemsOnNorthWestWhenLocationUpdated() {
        int distance = 10;
        var items = Stream.of(
            // Must be found on east
            givenItemWithListener(90,106),
            givenItemWithListener(90,93),

            // Must be found on north
            givenItemWithListener(93,90),
            givenItemWithListener(104,90)
        ).sorted().collect(Collectors.toList());

        var itemsFound = itemLocator.findItemsByDirection(new Position(100, 100), Direction.Northwest, distance)
                .sorted()
                .collect(Collectors.toList());
        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on Southeast when location updated")
    @Test
    public void shouldFindItemsOnSouthEastWhenLocationUpdated() {
        int distance = 10;
        var items = Stream.of(
            // Must be found on East ( x + distance )
            givenItemWithListener(110,106),
            givenItemWithListener(110,92),

            // Must be found on South ( y + distance )
            givenItemWithListener(93,110),
            givenItemWithListener(104,110)
        ).sorted().collect(Collectors.toList());

        var itemsFound = itemLocator.findItemsByDirection(new Position(100, 100), Direction.Southeast, distance)
                .sorted()
                .collect(Collectors.toList());
        assertIterableEquals(items, itemsFound);
    }

    @DisplayName("Should find items on Southwest when location updated")
    @Test
    public void shouldFindItemsOnSouthwestWhenLocationUpdated() {
        int distance = 10;
        var itemsInRange = Stream.of(
            // Must be found on east
            givenItemWithListener(90,106),
            givenItemWithListener(90,93),

            // Must be found on south
            givenItemWithListener(93,110),
            givenItemWithListener(107,110)
        ).sorted().collect(Collectors.toList());

        var itemsFound = itemLocator.findItemsByDirection(new Position(100, 100), Direction.Southwest, distance)
                .sorted()
                .collect(Collectors.toList());
        assertIterableEquals(itemsInRange, itemsFound);
    }

    // STUB GENERATORS

    private List<Item> givenItemsOnNorth(Point2D point, int distance) {
        return IntStream.range(point.getX() - distance, point.getX() + distance)
                .mapToObj(idx->givenItemWithoutListener(ThreadLocalRandom.current().nextInt(point.getX()-distance, point.getX()+distance), point.getY() - distance))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Item> givenItemsOnSouth(Point2D point, int distance) {
        return IntStream.range(point.getX() - distance, point.getX() + distance)
                .mapToObj(idx->givenItemWithoutListener(ThreadLocalRandom.current().nextInt(point.getX()-distance, point.getX()+distance), point.getY() + distance))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Item> givenItemsOnEast(Point2D point, int distance) {
        return IntStream.range(point.getY() - distance, point.getY() + distance)
                .mapToObj(idx->givenItemWithoutListener(point.getX() + distance, ThreadLocalRandom.current().nextInt(point.getY()-distance, point.getY()+distance)))
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Item> givenItemsOnWest(Point2D point, int distance) {
        return IntStream.range(point.getY() - distance, point.getY() + distance)
                .mapToObj(idx->givenItemWithoutListener(point.getX() - distance, ThreadLocalRandom.current().nextInt(point.getY() - distance, point.getY() + distance)))
                .sorted()
                .collect(Collectors.toList());
    }

    private Item givenItemWithoutListener(int x, int y) {
        var item = new UOItem(serialSequence.getAndIncrement(), 1)
                .location(x, y, 0);
        itemsBySerialId.put(item.getSerialId(), item);
        return item;
    }

    private Item givenItemWithListener(int x, int y) {
        var item = new UOItem(serialSequence.getAndIncrement(), 1);
        item.addPropertyChangeListener(itemLocator);
        item.location(x, y, 0);
        itemsBySerialId.put(item.getSerialId(), item);
        return item;
    }
}