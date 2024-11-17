package net.sf.juoserver.model.core;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOItem;
import net.sf.juoserver.model.UONpcMobile;
import net.sf.juoserver.protocol.MobileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The <b>Core</b> facade.
 * <p/>
 * Holds information about everything: tiles, mobiles, etc.
 */
public final class UOCore implements Core {
	private static final int MOBILES_MAX_SERIAL_ID = 0x3FFFFFFF;
	//TODO: make this private
	public static final int ITEMS_MAX_SERIAL_ID = MOBILES_MAX_SERIAL_ID + 1;
	private static final int OBJECTS_MAX_SERIAL_ID = 0x7FFFFFFF;


	/**
	 * Currently managed mobiles.
	 */
	private final Map<Integer, Mobile> mobilesBySerialId = new HashMap<>();
	
	/**
	 * Currently managed items.
	 */
	private final Map<Integer, Item> itemsBySerialId = new HashMap<>();
	private final Map<Item, Container> containersByContainedItems = new HashMap<>();
	/**
	 * Items serial
	 */
	private final AtomicInteger itemSerial = new AtomicInteger();
	private final ItemLocator itemLocator;


	/**
	 * The accounts, by username.
	 */
	private final Map<String, Account> accounts = new HashMap<>();
	private final Configuration configuration;
	private final FileReadersFactory fileReadersFactory;
	private final DataManager dataManager;

	/**
	 * Map reader.
	 */
	private MapFileReader mapReader;

	private final AtomicInteger atomicCursor = new AtomicInteger(1);
	/**
	 * Config Reader
	 */
	private ConfigFileReader configFileReader;

	/**
	 * Listener
	 */
	private final List<MobileListener> mobileListeners = new ArrayList<>();

	public UOCore(FileReadersFactory fileReadersFactory, DataManager dataManager, Configuration configuration, ConfigFileReader configFileReader) {
		super();
		this.configuration = configuration;
		this.fileReadersFactory = fileReadersFactory;
		this.dataManager = dataManager;
		this.configFileReader = configFileReader;
		this.itemLocator = new UOItemLocator(itemsBySerialId, configuration);
	}

	@Override
	public void init() {
		try {
			// TODO: coordinate the map index (the '0') with the number that we provide when we send
			// the GeneralInformationSetCursorHueSetMap message.
			var mulPath = configuration.getFiles().getMulPath();
			if (mulPath == null || !Files.exists(Path.of(mulPath))) {
				throw new LoadException("UO folder containing .mul files was not found, have you configured files.mulPath?");
			}
			mapReader = fileReadersFactory.createMapFileReader(new File(mulPath + File.separator + "map0.mul"), 4096);
		} catch (FileNotFoundException e) {
			throw new LoadException(e);
		}
		
		loadData();
	}

	private void addItems(Point2D mob, Collection<? extends Item> items) {
		for (Item it : items) {
			addItem(it);
			if (it instanceof Container) {
				Container container = (Container) it;
				addItems(mob, container.getItems());
				for (Item item : container.getItems()) {
					containersByContainedItems.put(item, container);
				}
			}
		}
	}

	private void addItem(Item item) {
		itemsBySerialId.put(item.getSerialId(), item);
		item.addPropertyChangeListener(itemLocator);
	}

	private void removeItem(Item item) {
		item.removePropertyChangeListener(itemLocator);
		itemsBySerialId.remove(item.getSerialId());
	}

	private void addMobile(Mobile mobile) {
		this.mobilesBySerialId.put(mobile.getSerialId(), mobile);
		for (MobileListener listener : mobileListeners) {
			listener.onMobileCreated(mobile);
		}
	}

	private void loadData() {

		for (Mobile mobile : dataManager.loadMobiles()) {
			addMobile(mobile);
		}
		
		for (Account account : dataManager.loadAccounts()) {
			accounts.put(account.getUsername(), account);
		}
		
		for (Mobile mob : mobilesBySerialId.values()) {
			addItems(mob, mob.getItems().values());
		}

		addItems(null, dataManager.loadItems());
		itemSerial.set(dataManager.getItemSerial());

		itemLocator.init();
	}
	
	/**
	 * Retrieves the map tile definition of the given coordinates.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return the map tile definition of the given coordinates
	 */
	@Override
	public MapTile getTile(final int x, final int y) {
		return mapReader.getEntryAt(new MapLocation() {
			@Override public int getX() { return x; }
			@Override public int getY() { return y; }
		});
	}
	
	@Override
	public Mobile findMobileByID(int serialID) {
		if (!isMobile(serialID)) {
			return null;
		}
		return mobilesBySerialId.get(serialID);
	}

	@Override
	public void removeMobile(Mobile mobile) {
		mobilesBySerialId.remove(mobile.getSerialId());
		for (MobileListener listener : mobileListeners) {
			listener.onMobileRemoved(mobile);
		}
	}

	@Override
	public Item findItemByID(int serialID) {
		if (!isItem(serialID)) {
			return null;
		}
		return itemsBySerialId.get(serialID);
	}
	
	private boolean isMobile(int serialID) {
		return serialID > 0 && serialID <= UOCore.MOBILES_MAX_SERIAL_ID;
	}

	private boolean isItem(int serialID) {
		return serialID >= UOCore.ITEMS_MAX_SERIAL_ID && serialID <= OBJECTS_MAX_SERIAL_ID;
	}

	/**
	 * Retrieves an {@link Account} by username and password; the password is
	 * needed to check the credentials.
	 * 
	 * @param username username
	 * @return the {@link Account} matching the provided username, or <tt>null</tt>
	 * if no such account could be found
	 */
	@Override
	public Account findAccountByUsername(String username) {
		return accounts.get(username);
	}
	
	/**
	 * Attempts to authenticate the given account with the specified password.
	 * 
	 * @param account account
	 * @param password password
	 * @return <tt>true</tt> if and only if the account could be authenticated
	 * with the provided password
	 */
	@Override
	public boolean authenticate(Account account, String password) {
		return password != null && password.equals( account.getPassword() ); 
	}

	@Override
	public Container findContainerByContainedItem(Item item) {
		return containersByContainedItems.get(item);
	}

	@Override
	public void removeItemFromContainer(Item item) {
		containersByContainedItems.remove(item);
	}

	@Override
	public void addItemToContainer(Item item, Container container) {
		containersByContainedItems.put(item, container);
	}

	@Override
	public Item createItem(int modelId) {
		var item = new UOItem(ITEMS_MAX_SERIAL_ID + itemSerial.getAndIncrement(), modelId);
		addItem(item);
		return item;
	}

	@Override
	public Collection<Item> findItemsByDirection(Point2D myLocation, Direction direction, int distanceFromMe) {
		return itemLocator.findItemsByDirection(myLocation, direction, distanceFromMe)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<Item> findItemsInRegion(Point2D location, int distance) {
		return itemLocator.findItemsInRegion(location, distance)
				.collect(Collectors.toList());
	}

	@Override
	public NpcMobile createNpcAtLocation(int templateId, Point3D location) {
		var mobile = (UONpcMobile) configFileReader.loadNpcs()
				.stream()
				.filter(npc->npc.getTemplateId() == templateId)
				.findFirst()
				.orElseThrow();
		mobile.setSerialId(itemSerial.getAndIncrement());
		mobile.setLocation(location);
		mobile.setStatusFlag(StatusFlag.UOML);
		mobile.setRaceFlag(RaceFlag.Human);
		mobile.setSexRace(SexRace.MaleHuman);
		addMobile(mobile);
		return mobile;
	}

	@Override
	public Collection<Mobile> findNpcInRange(Point2D location) {
		return mobilesBySerialId.values()
				.stream()
				.filter(mobile-> mobile.isNpc() && MobileUtils.getDistance(location, mobile) < configuration.getClient().getLos())
				.collect(Collectors.toList());
	}

	@Override
	public Stream<Mobile> findMobilesInRange(Point2D location) {
		return mobilesBySerialId.values().stream()
				.filter(mobile-> MobileUtils.getDistance(location, mobile) < configuration.getClient().getLos());
	}

	@Override
	public Stream<NpcMobile> getAllNpcs() {
		return mobilesBySerialId.values()
				.stream()
				.filter(Mobile::isNpc)
				.map(mobile -> (NpcMobile) mobile);
	}

	@Override
	public int getNextCursorId() {
		return atomicCursor.getAndIncrement();
	}

	@Override
	public void addMobileListener(MobileListener listener) {
		this.mobileListeners.add(listener);
	}

	@Override
	public void removeMobileCreationListener(MobileListener listener) {
		this.mobileListeners.remove(listener);
	}
}
