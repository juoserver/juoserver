package net.sf.juoserver.model;

import net.sf.juoserver.api.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class acts as a <b>mediator</b> between clients, providing the
 * means to coordinate them with each other thus implementing inter-client
 * communication.
 * <p/>
 * Clients are notified of what's happening in the other controllers
 * according to the <b>observer</b> pattern.
 */
public final class Intercom implements InterClientNetwork {
	private final List<IntercomListener> listeners = new CopyOnWriteArrayList<>();

	@Override
	public void addIntercomListener(IntercomListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeIntercomListener(IntercomListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies listeners about a mobile's movement.
	 * 
	 * @param movingMobile
	 *            the moving mobile
	 * @throws IntercomException
	 *             in case of inter-client communication errors
	 */
	@Override
	public void notifyOtherMobileMovement(Mobile movingMobile) {
		for (IntercomListener l : listeners) {
			l.onOtherMobileMovement(movingMobile);
		}
	}

	/**
	 * Notifies listeners that a mobile, {@code enteredMobile}, has entered
	 * another mobile's, {@code targetMobile}, range.
	 * 
	 * @param enteredMobile
	 *            the mobile who has entered the target's range
	 * @param targetMobile
	 *            the mobile whose range is being entered
	 * @throws IntercomException
	 *             in case of inter-client communication errors
	 */
	@Override
	public void notifyEnteredRange(Mobile enteredMobile, JUoEntity targetMobile) {
		for (IntercomListener l : listeners) {
			l.onEnteredRange(enteredMobile, targetMobile);
		}
	}

	@Override
	public void notifyOutOfRange(Mobile leavedMobile, JUoEntity targetMobile) {
		for (IntercomListener listener : listeners) {
			listener.onOutOfRange(leavedMobile, targetMobile);
		}
	}

	/**
	 * Notifies listeners about a mobile's speech.
	 * 
	 * @param speaker
	 *            the speaking mobile
	 * @param type
	 *            message type
	 * @param hue
	 *            message colour
	 * @param font
	 *            message font
	 * @param language
	 *            message language
	 * @param text
	 *            message text
	 * @throws IntercomException
	 *             in case of inter-client communication errors
	 */
	@Override
	public void notifyMobileSpeech(Mobile speaker, MessageType type, int hue,
			int font, String language, String text) {
		for (IntercomListener l : listeners) {
			l.onOtherMobileSpeech(speaker, type, hue, font, language, text);
		}
	}

	/**
	 * Notifies listeners about an item being dropped.
	 * 
	 * @param droppingMobile
	 *            mobile dropping the item
	 * @param item
	 *            item
	 * @param targetSerialId
	 *            target serial ID
	 */
	@Override
	public void notifyItemDropped(Mobile droppingMobile, Item item, int targetSerialId) {
		for (IntercomListener l : listeners) {
			l.onItemDropped(droppingMobile, item, targetSerialId);
		}
	}

	/**
	 * Notifies that the specified mobile has changed their clothes.
	 * 
	 * @param wearingMobile
	 *            the mobile changing their clothes
	 */
	@Override
	public void notifyChangedClothes(Mobile wearingMobile) {
		for (IntercomListener l : listeners) {
			l.onChangedClothes(wearingMobile);
		}
	}

	@Override
	public void notifyDroppedCloth(Mobile mobile, Item droppedCloth) {
		for (IntercomListener l : listeners) {
			l.onDroppedCloth(mobile, droppedCloth);
		}
	}
	
	/**
	 * Notifies that has changed warmode
	 * 
	 * @param mobile
	 * 		the mobile
	 */
	@Override
	public void notifyChangedWarMode(Mobile mobile) {
		for (IntercomListener l : listeners) {
			l.onChangedWarMode(mobile);
		}
	}

	/**
	 * Notifies that attack started
	 */
	@Override
	public void notifyAttackWithDamage(Mobile attacker, int damage, Mobile attacked) {
		for (IntercomListener l : listeners) {
			l.onAttack(attacker, damage, attacked);
		}
	}
	
	/**
	 * Notifies that attack finished
	 */
	@Override
	public void notifyAttackFinished(Mobile attacker, Mobile attacked) {		
		for (IntercomListener l : listeners) {
			l.onAttackFinished(attacker, attacked);
		}
	}

	@Override
	public void notifyOtherDamaged(Mobile mobile, int damage) {
		for (IntercomListener intercomListener : listeners) {
			intercomListener.onOtherDamaged(mobile, damage);
		}
	}

	@Override
	public void notifyGroundItemsCreated(Collection<Item> items) {
		for (IntercomListener intercomListener : listeners) {
			intercomListener.onGroundItemCreated(items);
		}
	}

	@Override
	public void notifyOtherKilled(Mobile mobile) {
		for (IntercomListener intercomListener : listeners) {
			intercomListener.onOtherKilled(mobile);
		}
	}

}
