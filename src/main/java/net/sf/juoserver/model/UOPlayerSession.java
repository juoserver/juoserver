package net.sf.juoserver.model;

import net.sf.juoserver.api.*;

import java.util.*;

public class UOPlayerSession implements PlayerSession {
	public static final int LINE_OF_SIGHT = 24;
	private final Core core;
	private final Account account;
	private final Set<Mobile> mobilesInRange = new HashSet<>();
	private final Set<Item> itemsInRange = new HashSet<>();
	/**
	 * Used to notify session client about his updates
	 */
	private final ModelOutputPort listener;
	/**
	 * Used to notify others clients about session update
	 */
	private final InterClientNetwork network;
	
	private Mobile mobile;
	
	protected Mobile attacking;
	protected final Set<Mobile> attackingMe = new HashSet<>();
	
	public UOPlayerSession(Core core, Account account, ModelOutputPort listener,
			InterClientNetwork network) {
		super();
		this.core = core;
		this.account = account;
		this.listener = listener;
		this.network = network;
	}

	@Override
	public Set<Mobile> getMobilesInRange() {
		return mobilesInRange;
	}

	@Override
	public List<String> getCharacterNames() {
		List<String> names = new ArrayList<>();
		for (int serialId : account.getCharactersSerials()) {
			Mobile mobile = core.findMobileByID(serialId);
			names.add(mobile.getName());
		}
		return names;
	}

	@Override
	public void selectCharacterById(int charPosition) {
		mobile = core.findMobileByID( account.getCharacterSerialIdByPosition(charPosition) );
	}

	@Override
	public Mobile getMobile() {
		return mobile;
	}

	@Override
	public GameStatus startGame() {
		return new UOGameStatus(LightLevels.Day, Season.Spring);
	}

	@Override
	public void move(Direction direction, boolean running) {
		boolean onlyChangingDirection = isOnlyChangingDirection(direction);
		
		mobile.setDirection(direction);
		mobile.setRunning(running);
		
		if (!onlyChangingDirection) {
			mobile.moveForward();
		}

		var items = core.findItemsByDirection(mobile, direction, 20);
		if (!items.isEmpty()) {
			network.notifyGroundItemsCreated(items);
		}

		MapTile tile = core.getTile(mobile.getX(), mobile.getY());
		mobile.setZ( tile.getZ() );

		// Notify others client I'm moving
		network.notifyOtherMobileMovement(mobile);
	}

	private boolean isOnlyChangingDirection(Direction direction) {
		return direction != mobile.getDirection();
	}

	@Override
	public void onOtherMobileMovement(Mobile moving) {
		int los = LINE_OF_SIGHT;

		int distance = (int) Math.hypot(mobile.getX() - moving.getX(), mobile.getY() - moving.getY());

		boolean outOfSight = distance > los;

		if (moving.equals( mobile ) || (outOfSight && !mobilesInRange.contains(moving))) {
			return;
		}

		// If the moving mobile is within range, draw it
		if (!mobilesInRange.contains( moving )) {
			onEnteredRange(moving, mobile);
			// Instruct the moving mobile's client to do the same
			network.notifyEnteredRange(mobile, moving);
		} else if (outOfSight) {
			// Moving just got out of mobile sight
			onOutOfRange(moving, mobile);
			// notify the moving mobile
			network.notifyOutOfRange(mobile, moving);
		}
		
		// Always send an update
		listener.mobileChanged(moving);
	}

	@Override
	public void onEnteredRange(Mobile entered, JUoEntity target) {
		if (!target.equals( mobile )) {
			return; // Point-to-point semantics
		}
		
		mobilesInRange.add( entered );
		listener.mobileApproached(entered);
	}

	@Override
	public void onOutOfRange(Mobile exited, JUoEntity target) {
		if (!target.equals(mobile)) {
			return;
		}
		mobilesInRange.remove(exited);
		listener.mobileGotAway(exited);
	}

	@Override
	public void speak(MessageType messageType, int hue, int font, String language, String text) {
		network.notifyMobileSpeech(mobile, messageType, hue, font, language, text);
		// Don't do anything just right now. The onOtherMobileSpeech()
		// method will bring the message to everybody, including myself.
		
		// NOTE on semantics: we could have returned a UnicodeSpeech here,
		// and skip self-notifications on the onOtherMobileSpeech() method,
		// like we do in the onOtherMobileMovement() method.
	}
	
	// This will be called both on the speaker's controller (thus letting it
	// hear what its mobile is saying) and the other listener controllers,
	// thus letting the others hear it.
	@Override
	public void onOtherMobileSpeech(Mobile speaker, MessageType type, int hue,
			int font, String language, String text) {
		listener.mobileSpoke(speaker, type, hue, font, language, text);
	}

	@Override
	public void dropItem(int itemSerial, boolean droppedOnTheGround,
			int targetContainerSerial, Point3D targetPosition) {
		Item droppedItem = core.findItemByID(itemSerial);
		
		if (mobile.removeItem(droppedItem)) {
			network.notifyDroppedCloth(mobile, droppedItem);
		}
		
		removeFromSourceContainer(droppedItem);
		
		if (!droppedOnTheGround) { 
			addToTargetContainer(droppedItem, targetContainerSerial, targetPosition);
		}
		
		if (droppedOnTheGround) {
			droppedItem.location(targetPosition);
			network.notifyItemDropped(mobile, droppedItem, 0);
		}
	}
	
	private void removeFromSourceContainer(Item droppedItem) {
		Container sourceContainer = core.findContainerByContainedItem(droppedItem);
		if (sourceContainer != null) {
			sourceContainer.removeItem(droppedItem);
			core.removeItemFromContainer(droppedItem);
			listener.containerChangedContents(sourceContainer);
		}
	}

	private void addToTargetContainer(Item droppedItem, int targetContainerSerial, Point3D targetPosition) {
		Container targetContainer = (Container) core.findItemByID(targetContainerSerial);
		targetContainer.addItem(droppedItem, new Position(targetPosition.getX(), targetPosition.getY()));
		core.addItemToContainer(droppedItem, targetContainer);
		listener.containerChangedContents(targetContainer);
	}

	@Override
	public void onItemDropped(Mobile droppingMobile, Item item, int targetSerialId) {
		if (!mobile.equals(droppingMobile)) {
			listener.itemDragged(item, droppingMobile, targetSerialId);
		}
		listener.itemChanged(item);
	}

	@Override
	public void wearItemOnMobile(Layer layer, int itemSerialId) {
		Item item = core.findItemByID(itemSerialId);
		
		mobile.setItemOnLayer(layer, item);
		removeFromSourceContainer(item);
		
		network.notifyChangedClothes(mobile);
	}
	
	@Override
	public void onChangedClothes(Mobile wearingMobile) {
		listener.mobileChangedClothes(wearingMobile);
	}

	@Override
	public void onDroppedCloth(Mobile mobile, Item droppedCloth) {
		listener.mobileDroppedCloth(mobile, droppedCloth);
	}
	
	@Override
	public void toggleWarMode(boolean isWarOn) {
		if (isWarOn) {
			mobile.setCharacterStatus(CharacterStatus.WarMode);			
		} else {
			if (isAttackingSomeone()) {
				network.notifyAttackFinished(mobile, attacking);
			}
			mobile.setCharacterStatus(CharacterStatus.Normal);
		}
		network.notifyChangedWarMode(mobile);		
	}
	
	private boolean isAttackingSomeone() {
		return attacking!=null;
	}
	
	@Override
	public void onChangedWarMode(Mobile mobile) {
		listener.mobileChangedWarMode(mobile);
	}

	@Override
	public void attack(Mobile opponent) {
		network.notifyAttacked(mobile, opponent);
	}
	
	@Override
	public void onAttacked(Mobile attacker, Mobile attacked) {			
		if (mobile.equals(attacked)) {
			attackingMe.add(attacker);			
			listener.mobileAttacked(attacker);
		} else {
			if (mobile.equals(attacker)) {
				attacking = attacked;
			}
		}		
	}	
	
	@Override
	public void onAttackFinished(Mobile attacker, Mobile attacked) {		
		if (mobile.equals(attacker)) {
			attacking = null;
			if (!attackingMe.contains(attacked)) {
				listener.mobileAttackFinished(attacked);
			}			
		} else {
			if (mobile.equals(attacked)) {
				attackingMe.remove(attacker);
				if (attacking == null) {
					listener.mobileAttackFinished(attacker);
				}				
			}
		}
	}

	@Override
	public void applyDamage(int damage, Mobile opponent) {
		mobile.setCurrentHitPoints( Math.max(mobile.getCurrentHitPoints() - damage, 0) );
		if (mobile.getCurrentHitPoints() == 0) {
			mobile.kill();

			// Clear combat
			attacking = null;
			attackingMe.clear();

			listener.mobiledKilled(mobile);
			network.notifyOtherKilled(mobile);
		} else {
			listener.mobileDamaged(mobile, damage, opponent);
			network.notifyOtherDamaged(mobile, damage, opponent);
		}
	}

	@Override
	public void onOtherDamaged(Mobile mobile, int damage, Mobile opponent) {
		listener.mobileDamaged(mobile, damage, opponent);
	}

	@Override
	public void onOtherKilled(Mobile mobile) {
		listener.mobiledKilled(mobile);
	}

	@Override
	public void showGroundItems(Collection<Item> items) {
		network.notifyGroundItemsCreated(items);
	}

	@Override
	public void onGroundItemCreated(Collection<Item> items) {
		listener.groundItemsCreated(items);
	}


}
