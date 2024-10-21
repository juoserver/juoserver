package net.sf.juoserver.model.core;

import net.sf.juoserver.api.*;
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
    private final Configuration configuration;
    private final Map<Point2D, Set<Item>> itemsByLocation;

    public UOItemLocator(Map<Integer, Item> itemsBySerialId, Configuration configuration) {
        this.itemsBySerialId = itemsBySerialId;
        this.configuration = configuration;
        this.itemsByLocation = new HashMap<>();
    }

    /**
     * Initialize indexes for provided items
     */
    @Override
    public void init() {
        var initialInstant = Instant.now();

        var grouped = itemsBySerialId.values().parallelStream()
                .flatMap(item->Stream.of(new AbstractMap.SimpleEntry<>(new Position(item.getX(), item.getY()), item)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));
        this.itemsByLocation.putAll(grouped);

        var finalInstant = Instant.now();
        var numberOfItems = itemsBySerialId.keySet().size();
        LOGGER.info("{} items indexed in {} millis", numberOfItems, Duration.between(initialInstant, finalInstant).toMillis());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("location".equals(evt.getPropertyName())) {
            itemsByLocation.computeIfPresent(new Position((Point2D) evt.getOldValue()), (location,items)->{
                items.remove((Item) evt.getSource());
                return items.isEmpty() ? null : items;
            });
            itemsByLocation.computeIfAbsent(new Position((Point2D) evt.getNewValue()), location->{
                Set<Item> items = new HashSet<>();
                items.add((Item) evt.getSource());
                return items;
            });
        }
    }

    @Override
    public Stream<Item> findItemsByDirection(Point2D location, Direction direction, int distance) {
        switch (direction) {
            case East: return filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() + distance, y))
                    .distinct();

            case West: return filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() - distance, y))
                    .distinct();

            case North: return filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() - distance))
                    .distinct();

            case South: return filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() + distance))
                    .distinct();

            case Southwest: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() - distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() + distance)))
                    .distinct();

            case Southeast: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() + distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() + distance)))
                    .distinct();

            case Northwest: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() - distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() - distance)))
                    .distinct();

            case Northeast: return Stream.concat(filterItems(location.getY() - distance, location.getY() + distance, y->new Position(location.getX() + distance, y)),
                    filterItems(location.getX() - distance, location.getX() + distance, x->new Position(x, location.getY() - distance)))
                    .distinct();
            default: return Stream.empty();
        }
    }

    private Stream<Item> filterItems(int from, int to, IntFunction<Point2D> objFunction) {
        return IntStream.rangeClosed(from, to)
                .mapToObj(objFunction)
                .map(itemsByLocation::get)
                .filter(Objects::nonNull)
                .flatMap(Set::stream);
    }

    @Override
    public Stream<Item> findItemsInRegion(Point2D location, int distance) {
        var items = new HashSet<Item>();
        var locX = location.getX();
        var locY = location.getY();
        for (int x=locX - distance; x<locX + distance; x++) {
            for (int y=locY - distance; y< locY + distance; y++) {
                items.addAll(itemsByLocation.getOrDefault(new Position(x, y), Collections.emptySet()));
            }
        }
        return items.stream();
    }
}
