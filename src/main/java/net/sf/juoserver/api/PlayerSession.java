package net.sf.juoserver.api;

import net.sf.juoserver.protocol.Cursor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlayerSession extends IntercomListener, CombatSession {
	List<String> getCharacterNames();
	void selectCharacterById(int charId);
	GameStatus startGame();
	Mobile getMobile();	
	void move(Direction direction, boolean running);
	void speak(MessageType messageType, int hue, int font, String language, String text);
	void dropItem(int itemSerial, boolean droppedOnTheGround, int targetContainerSerial, Point3D targetPosition);
	void wearItemOnMobile(Layer layer, int itemSerialId);
	void toggleWarMode(boolean war);
	void attack(Mobile opponent);
	void applyDamage(int damage, Mobile opponent);
	void applyDamageTo(Mobile opponent, int damage);
	void showGroundItems(Collection<Item> items);
	Collection<Mobile> getMobilesInRange();

	/**
	 * Send a cursor to the session
	 * @param type Type of cursor
	 * @param target Location or Object
	 * @return Future for the cursor
	 */
	CompletableFuture<Cursor> sendCursor(CursorType type, CursorTarget target);
	void selectCursor(Cursor cursor);
}
