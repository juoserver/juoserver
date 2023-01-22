package net.sf.juoserver.protocol;

import net.sf.juoserver.api.CharacterStatus;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarModeTest {

    @DisplayName("Should match war mode")
    @Test
    public void shouldMatchWarMode() throws DecoderException {
        var hex = "7201002000";
        var warmode = new WarMode(Hex.decodeHex(hex));
        assertEquals(new WarMode(CharacterStatus.WarMode), warmode);
    }

    @DisplayName("Should match normal mode")
    @Test
    public void shouldMatchNormalMode() throws DecoderException {
        var hex = "7200002000";
        var normalMode = new WarMode(Hex.decodeHex(hex));
        assertEquals(new WarMode(CharacterStatus.Normal), normalMode);
    }
}
