package net.sf.juoserver.protocol;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PickUpItemTest {
	
	@Test
	public void receivePickUpItem() throws DecoderException {
		byte[] bytes = Hex.decodeHex( "074000001A0001".toCharArray() );
		PickUpItem pickUpItem = new PickUpItem(bytes);
		assertEquals(0x4000001A, pickUpItem.getItemSerialId());
		assertEquals(1, pickUpItem.getAmount());
	}
}
