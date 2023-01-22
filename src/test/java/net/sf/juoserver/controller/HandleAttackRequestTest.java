package net.sf.juoserver.controller;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.protocol.AttackOK;
import net.sf.juoserver.protocol.AttackRequest;
import net.sf.juoserver.protocol.AttackSucceed;
import net.sf.juoserver.protocol.FightOccurring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HandleAttackRequestTest extends AbstractNewGameControllerTest {

    private AttackRequest attackRequest;
    private Mobile attacked = TestingFactory.createTestMobile(50, "loller");

    @BeforeEach
    public void setUp() {
        var buffer = ByteBuffer.allocate(5)
            .put((byte)0x05)
            .putInt(attacked.getSerialId());
        attackRequest = new AttackRequest(buffer.array());

        when(core.findMobileByID(attacked.getSerialId()))
                .thenReturn(attacked);
    }

    @DisplayName("Should begin combat without damage")
    @Test
    public void shouldBeginCombatWithoutDamage() {
        var messages = gameController.handle(attackRequest);
        verify(session).attack(attacked);

        assertIterableEquals(List.of(new AttackOK(attacked),
                new FightOccurring(mobile, attacked),
                new AttackSucceed(attacked)), messages);
    }

}
