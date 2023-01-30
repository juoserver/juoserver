package net.sf.juoserver.model;

import net.sf.juoserver.api.MessageType;
import org.jmock.Expectations;
import org.junit.Test;

import java.io.IOException;

public class SpeechComponentTest extends AbstractComponentTest {
	@Test
	public void speechIsBroadcasted() throws IOException {
		context.checking(new Expectations() {{
			oneOf(asderListener).mobileSpoke(asder, MessageType.Regular, 3, 4, "ENG", "Hi there!");
			oneOf(lollerListener).mobileSpoke(asder, MessageType.Regular, 3, 4, "ENG", "Hi there!");
		}});
		
		asderSession.speak(MessageType.Regular, 3, 4, "ENG", "Hi there!");
	}
}
