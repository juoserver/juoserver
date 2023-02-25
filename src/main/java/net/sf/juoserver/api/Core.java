package net.sf.juoserver.api;

import net.sf.juoserver.model.UOItem;

import java.util.Collection;

/**
 * The core main API.
 */
public interface Core {

	/**
	 * Initializes the core.
	 * 
	 * @throws LoadException in case something went wrong
	 */
	void init();

	/**
	 * Retrieves the map tile definition of the given coordinates.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return the map tile definition of the given coordinates
	 */
	MapTile getTile(final int x, final int y);

	/**
	 * Retrieves a {@link Mobile} by its serial ID.
	 * 
	 * @param serialID {@link Mobile}'s serial ID
	 * @return the {@link Mobile} with the specified serial ID,
	 * or <tt>null</tt> if there is no such {@link Mobile}
	 */
	Mobile findMobileByID(int serialID);

	/**
	 * Retrieves an {@link UOItem} by its serial ID.
	 * 
	 * @param serialID {@link UOItem}'s serial ID
	 * @return the {@link UOItem} with the specified serial ID,
	 * or <tt>null</tt> if there is no such {@link UOItem}
	 */
	Item findItemByID(int serialID);
	
	/**
	 * Retrieves an {@link Account} by username and password; the password is
	 * needed to check the credentials.
	 * 
	 * @param username username
	 * @return the {@link Account} matching the provided username, or <tt>null</tt>
	 * if no such account could be found
	 */
	Account findAccountByUsername(String username);

	/**
	 * Attempts to authenticate the given account with the specified password.
	 * 
	 * @param account account
	 * @param password password
	 * @return <tt>true</tt> if and only if the account could be authenticated
	 * with the provided password
	 */
	boolean authenticate(Account account, String password);

	Container findContainerByContainedItem(Item item);

	void addItemToContainer(Item item, Container container);

	void removeItemFromContainer(Item item);

	Item createItem(int modelId);

	/**
	 * Find items in a specific direction of the location.
	 * @param myLocation origin location
	 * @param direction direction to look for items
	 * @param distanceFromMe How far from the origin location will look for items
	 * @return List of items
	 */
	Collection<Item> findItemsByDirection(Point2D myLocation, Direction direction, int distanceFromMe);

	Collection<Item> findItemsInRegion(Point2D location, int distance);
}
