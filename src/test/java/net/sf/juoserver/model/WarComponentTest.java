package net.sf.juoserver.model;

import net.sf.juoserver.api.CharacterStatus;
import org.jmock.Expectations;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WarComponentTest extends AbstractComponentTest {
	@Test
	public void dropItemOnTheGround() throws IOException {
		assertEquals(CharacterStatus.Normal, asder.getCharacterStatus());
		
		context.checking(new Expectations() {{
			oneOf(asderListener).mobileChangedWarMode(asder);
			oneOf(lollerListener).mobileChangedWarMode(asder);
		}});
		
		asderSession.toggleWarMode(true);
		assertEquals(CharacterStatus.WarMode, asder.getCharacterStatus());
	}
}
