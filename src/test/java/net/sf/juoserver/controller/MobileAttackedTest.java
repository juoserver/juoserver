package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.Message;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.protocol.AttackOK;
import net.sf.juoserver.protocol.AttackSucceed;
import net.sf.juoserver.protocol.FightOccurring;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.verify;

public class MobileAttackedTest extends AbstractNewGameControllerTest {

    private Mobile attacker = TestingFactory.createTestMobile(50, "loller");

    @DisplayName("Mobile attacked without damage")
    @Test
    public void mobileAttackedWithoutDamage() throws IOException {
        gameController.mobileAttack(attacker);
        var captor = ArgumentCaptor.forClass(Object.class);

        verify(clientHandler).sendToClient((Message[]) captor.capture());

        assertIterableEquals(List.of(new AttackOK(attacker.getSerialId()),
                new AttackSucceed(attacker),
                new FightOccurring(mobile, attacker)), captor.getAllValues());
    }

    @DisplayName("Mobile attacked with damage")
    @Test
    public void mobileAttackedWithDamage() {
        gameController.mobileAttack(attacker);

        verify(combatSystem).defenseStarted(session, attacker);
    }
}
