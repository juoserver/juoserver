package net.sf.juoserver.model.core;

import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.ItemLocator;
import net.sf.juoserver.api.Point2D;
import net.sf.juoserver.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UOItemLocator implements ItemLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UOItemLocator.class);
    private final Map<Integer, Item> itemsBySerialId;
    private final Map<Point2D, List<Item>> itemsByLocation;

    public UOItemLocator(Map<Integer, Item> itemsBySerialId) {
        this.itemsBySerialId = itemsBySerialId;
        this.itemsByLocation = new HashMap<>();
    }

    /**
     * Initialize indexes for provided items
     */
    @Override
    public void init() {
        var initialInstant = Instant.now();
        var grouped = itemsBySerialId.values().parallelStream()
                .collect(Collectors.groupingBy(item->new Position(item.getX(), item.getY())));
        this.itemsByLocation.putAll(grouped);
        var finalInstant = Instant.now();
        var numberOfItems = itemsBySerialId.keySet().size();
        LOGGER.info("{} items indexed in {} millis", numberOfItems, Duration.between(initialInstant, finalInstant).toMillis());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("location".equals(evt.getPropertyName())) {
            itemsByLocation.computeIfPresent(new Position((Point2D) evt.getOldValue()), (location,col)->{
                col.remove((Item) evt.getSource());
                return null;
            });
            itemsByLocation.computeIfAbsent(new Position((Point2D) evt.getNewValue()), location->{
                List<Item> items = new ArrayList<>();
                items.add((Item) evt.getSource());
                return items;
            });
        }
    }

    @Override
    public Stream<Item> loadItemsByDirection(Point2D location, Direction direction, int distance) {
        switch (direction) {
            case East: return filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() + distance, y));
            case West: return filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() - distance, y));
            case North: return filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() - distance));
            case South: return filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() + distance));
            case Southwest: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() - distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() + distance)));
            case Southeast: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() + distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() + distance)));
            case Northwest: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() - distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() - distance)));
            case Northeast: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() + distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() - distance)));
            default: return Stream.empty();
        }
    }

    private Stream<Item> filterItems(int from, int to, IntFunction<Point2D> objFunction) {
        return IntStream.range(from, to)
                .mapToObj(objFunction)
                .map(itemsByLocation::get)
                .filter(Objects::nonNull)
                .flatMap(List::stream);
    }
}
