package net.sf.juoserver.api;

import java.util.Collection;

public interface InterClientNetwork {
	void notifyOtherMobileMovement(Mobile movingMobile);
	void notifyEnteredRange(Mobile enteredMobile, JUoEntity targetMobile);
	void notifyMobileSpeech(Mobile speaker, MessageType type, int hue, int font, String language, String text);
	void notifyChangedClothes(Mobile wearingMobile);
	void notifyItemDropped(Mobile droppingMobile, Item item, int targetSerialId);
	void notifyChangedWarMode(Mobile mobile);
	void notifyDroppedCloth(Mobile mobile, Item droppedCloth);
	void notifyAttacked(Mobile attacker, Mobile attacked);
	void notifyAttackFinished(Mobile attacker, Mobile attacked);
	void notifyOtherDamaged(Mobile mobile, int damage);
	void notifyFightOccurring(Mobile opponent1, Mobile opponent2);
	void removeIntercomListener(IntercomListener listener);
	void addIntercomListener(IntercomListener listener);
	void notifyGroundItemsCreated(Collection<Item> items);
}
