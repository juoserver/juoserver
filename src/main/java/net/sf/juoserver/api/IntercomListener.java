package net.sf.juoserver.api;


import java.util.Collection;

/**
 * Definition of an actor interested in receiving
 * notifications about other mobiles from the other
 * connected clients.
 */
public interface IntercomListener {
	void onOtherMobileMovement(Mobile movingMobile);
	void onEnteredRange(Mobile entered, JUoEntity target);
	void onOutOfRange(Mobile exited, JUoEntity target);
	void onOtherMobileSpeech(Mobile speaker, MessageType type, int hue, int font, String language, String text);
	void onChangedClothes(Mobile wearingMobile);
	void onDroppedCloth(Mobile mobile, Item droppedCloth);
	void onItemDropped(Mobile droppingMobile, Item item, int targetSerialId);
	void onChangedWarMode(Mobile mobile);
	void onAttacked(Mobile attacker, Mobile attacked);
	void onAttackFinished(Mobile attacker, Mobile attacked);
	void onOtherDamaged(Mobile mobile, int damage);
	void onGroundItemCreated(Collection<Item> items);
	void onOtherKilled(Mobile mobile);

}
