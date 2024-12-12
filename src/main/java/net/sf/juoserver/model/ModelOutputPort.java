package net.sf.juoserver.model;

import net.sf.juoserver.api.*;

import java.util.Collection;

/**
 * Model output boundary port. This interface is used by the model
 * to update the client about changes in the game.
 */
public interface ModelOutputPort {
	void mobileChanged(Mobile mobile);
	void mobileApproached(Mobile entered);
	void mobileGotAway(Mobile mobile);
	void mobileSpoke(Mobile speaker, MessageType type, int hue, int font, String language, String text);
	void containerChangedContents(Container updatedContainer);
	void itemDragged(Item item, Mobile droppingMobile, int targetSerialId);
	void itemChanged(Item item);
	void groundItemsCreated(Collection<Item> items);
	void mobileChangedClothes(Mobile mobile);
	void mobileChangedWarMode(Mobile mobile);
	void mobileAttack(Mobile attacker, int attackerDamage, Mobile attacked);
	void mobileAttackFinished(Mobile enemy);
	void mobileDroppedCloth(Mobile mobile, Item droppedCloth);
	void mobileDamaged(Mobile mobile, int damage);
	void mobiledKilled(Mobile mobile);
	void npcOnRange(Collection<Mobile> npcs);
	void sendCursor(int cursorId, CursorType type, CursorTarget target);
}
