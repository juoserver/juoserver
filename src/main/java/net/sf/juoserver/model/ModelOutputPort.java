package net.sf.juoserver.model;

import net.sf.juoserver.api.Container;
import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.MessageType;
import net.sf.juoserver.api.Mobile;

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
	void mobileAttacked(Mobile attacker);
	void mobileAttackFinished(Mobile enemy);
	void mobileDroppedCloth(Mobile mobile, Item droppedCloth);
	void mobileDamaged(Mobile mobile, int damage, Mobile opponent);
	void mobiledKilled(Mobile mobile);

}
