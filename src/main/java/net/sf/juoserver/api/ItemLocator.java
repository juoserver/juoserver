package net.sf.juoserver.api;

import java.beans.PropertyChangeListener;
import java.util.stream.Stream;

/**
 * This interface will handle searches for items in the core, it creates indexes to improve search performances in memory
 */
public interface ItemLocator extends PropertyChangeListener {


    /**
     * Initialize indexes for provided items
     */
    void init();

    /**
     * It will load all items at the location + distance in the direction the user is looking.
     * @param location Origin position
     * @param direction Direction for search items
     * @param distance distance to look for items
     * @return Stream of items found
     */
    Stream<Item> findItemsByDirection(Point2D location, Direction direction, int distance);

    /**
     * Find items in the area surrounding the informed location
     * @param location Location
     * @param distance Distance of the search
     * @return Stream of items found
     */
    Stream<Item> findItemsInRegion(Point2D location, int distance);
}
