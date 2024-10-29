package net.sf.juoserver.api;

import java.util.Collection;
import java.util.List;

public interface InterClientNetwork {
	void notifyOtherMobileMovement(Mobile movingMobile);
	void notifyEnteredRange(Mobile enteredMobile, JUoEntity targetMobile);
	void notifyOutOfRange(Mobile leavedMobile, JUoEntity targetMobile);
	void notifyMobileSpeech(Mobile speaker, MessageType type, int hue, int font, String language, String text);
	void notifyChangedClothes(Mobile wearingMobile);
	void notifyItemDropped(Mobile droppingMobile, Item item, int targetSerialId);
	void notifyChangedWarMode(Mobile mobile);
	void notifyDroppedCloth(Mobile mobile, Item droppedCloth);
	void notifyAttacked(Mobile attacker, Mobile attacked);
	void notifyAttackFinished(Mobile attacker, Mobile attacked);
	void notifyOtherDamaged(Mobile mobile, int damage, Mobile opponent);
	void notifyGroundItemsCreated(Collection<Item> items);
	void notifyOtherKilled(Mobile mobile);
	void removeIntercomListener(IntercomListener listener);
	void addIntercomListener(IntercomListener listener);

}
