package net.sf.juoserver.protocol;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

public class DropItemTest {

	@Test
	public void dropOnTheGround() throws DecoderException {
		byte[] bytes = HexFormat.of().parseHex( "08400004B117D406310000FFFFFFFF");
		DropItem di = new DropItem(bytes);
		Assertions.assertEquals(0x400004B1, di.getItemSerial());
		assertEquals(0x17D4, di.getTargetX());
		assertEquals(0x0631, di.getTargetY());
		assertEquals(0, di.getTargetZ());
		assertEquals(0, di.getBackpackGridIndex());
		assertEquals(0xFFFFFFFF, di.getTargetContainerSerial());
		assertTrue(di.isDroppedOnTheGround());
	}
	
	@Test
	public void dropOnContainer() throws DecoderException {
		byte[] bytes = HexFormat.of().parseHex( "08400004B1003E00610000400003E9");
		DropItem di = new DropItem(bytes);
		assertEquals(0x400004B1, di.getItemSerial());
		assertEquals(0x003E, di.getTargetX());
		assertEquals(0x0061, di.getTargetY());
		assertEquals(0, di.getTargetZ());
		assertEquals(0, di.getBackpackGridIndex());
		assertEquals(0x400003E9, di.getTargetContainerSerial());
		assertFalse(di.isDroppedOnTheGround());
	}
	
}
