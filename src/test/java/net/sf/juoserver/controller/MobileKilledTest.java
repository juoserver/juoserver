package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.protocol.AttackSucceed;
import net.sf.juoserver.protocol.CharacterDraw;
import net.sf.juoserver.protocol.DeathAnimation;
import net.sf.juoserver.protocol.StatusBarInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class MobileKilledTest extends MockitoAbstractGameControllerTest {

    @Test
    public void shouldSendMobileUpdateWhenSessionMobileKilled() throws IOException {
        gameController.mobiledKilled(mobile);
        verify(combatSystem).mobileKilled(mobile);
        verify(clientHandler).sendToClient(new DeathAnimation(mobile, 0x1FFD), new CharacterDraw(mobile),
                new StatusBarInfo(mobile),
                new AttackSucceed(0));
    }

    @Test
    public void shouldSendMobileUpdateWhenOtherMobileKilled() throws IOException {
        var otherMobile = TestingFactory.createTestMobile(-1,"Loller");
        gameController.mobiledKilled(otherMobile);

        verify(combatSystem, never()).mobileKilled(any(Mobile.class));

        verify(clientHandler).sendToClient(new DeathAnimation(otherMobile, 0x1FFD), new CharacterDraw(otherMobile),
                new StatusBarInfo(otherMobile),
                new AttackSucceed(0));
    }

}
