package net.sf.juoserver.protocol;

import net.sf.juoserver.protocol.CircularClientMovementTracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClientMovementTrackerTest {

	private CircularClientMovementTracker tracker;
	
	@BeforeEach
	public void createTracker() {
		tracker = new CircularClientMovementTracker();
	}
	
	@Test
	public void firstValueIsZero() {
		assertEquals(0, tracker.getExpectedSequence());
	}
	
	@Test
	public void anyValueGetIncremented() {
		tracker.setSequence(42);
		tracker.incrementExpectedSequence();
		assertEquals(43, tracker.getExpectedSequence());
	}
	
	@Test
	public void restartsFromOne() {
		tracker.setSequence(255);
		tracker.incrementExpectedSequence();
		assertEquals(1, tracker.getExpectedSequence());
	}
	
}
