package net.sf.juoserver.api;

import java.util.Map;
import java.util.Set;

public interface Mobile extends JUoEntity, Point3D {
	void setZ(int z);

	Mobile location(int x, int y, int z);

	Direction getDirection();

	void setDirection(Direction direction);

	boolean isRunning();

	void setRunning(boolean running);

	int getCurrentHitPoints();

	void setCurrentHitPoints(int currentHitPoints);

	int getMaxHitPoints();

	boolean isNameChangeFlag();

	StatusFlag getStatusFlag();

	SexRace getSexRace();

	int getStrength();

	int getDexterity();

	int getIntelligence();

	int getCurrentStamina();

	int getMaxStamina();

	int getCurrentMana();

	int getMaxMana();

	int getGoldInPack();

	int getArmorRating();

	int getWeight();

	int getMaxWeight();

	RaceFlag getRaceFlag();

	String getTitle();

	Set<Skill> getSkills();

	Map<Layer, Item> getItems();

	Notoriety getNotoriety();

	CharacterStatus getCharacterStatus();

	/**
	 * Updates this mobile's position according to their {@link Direction}.
	 */
	void moveForward();

	String getPrefixNameSuffix();

	byte getDirectionWithRunningInfo();

	void setItemOnLayer(Layer layer, Item item);

	Item getItemByLayer(Layer layer);

	boolean removeItem(Item item);

	Layer getLayer(Item item);

	void emptyLayer(Layer layer);
	
	void setCharacterStatus(CharacterStatus characterStatus);

	int getWeaponBaseDamage();

	int distanceOf(Mobile mobile);

	void kill();

	void revive();

	boolean isDeath();

	boolean isNpc();
}