package net.sf.juoserver.controller;

import net.sf.juoserver.api.MessageType;
import net.sf.juoserver.protocol.UnicodeSpeechRequest;
import org.jmock.Expectations;
import org.junit.Test;

import java.io.IOException;

public class SpeechTest extends AbstractGameControllerTest {
	@Test
	public void firstClientSpeaks() throws IOException {
		context.checking(new Expectations() {{
			oneOf(session).speak(MessageType.Regular, 3, 4, "ENG", "Hi there!");
		}});
		gameController.handle( new UnicodeSpeechRequest(MessageType.Regular, 3, 4, "ENG", "Hi there!") );
	}
}
