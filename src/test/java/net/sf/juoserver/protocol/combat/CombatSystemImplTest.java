package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.PhysicalDamageCalculator;
import net.sf.juoserver.api.PlayerSession;
import net.sf.juoserver.protocol.combat.CombatSystemImpl.KeyPair;
import net.sf.juoserver.protocol.combat.CombatSystemImpl.ValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CombatSystemImplTest {

    private CombatSystemImpl combatSystem;
    @Mock
    private PlayerSession attackerSession;
    private Mobile attackerMobile;
    @Mock
    private PlayerSession attackedSession;
    private Mobile attackedMobile;
    @Mock
    private PhysicalDamageCalculator physicalDamageCalculator;

    @BeforeEach
    public void setUp() {
        combatSystem = new CombatSystemImpl(physicalDamageCalculator);

        attackerMobile = TestingFactory.createTestMobile(1, "asder");
        lenient().when(attackerSession.getMobile())
                .thenReturn(attackerMobile);

        attackedMobile = TestingFactory.createTestMobile(1, "asder");
        lenient().when(attackedSession.getMobile())
                .thenReturn(attackedMobile);
    }

    @Test
    public void shouldContainSessionOnAttackStarted() {
        combatSystem.attackStarted(attackerSession, attackedMobile);
        assertTrue(combatSystem.getCombatSessions().containsKey(KeyPair.of(attackerMobile, attackedMobile)));
    }

    @Test
    public void shouldCombineSessionsOnDefenseStarted() {
        combatSystem.attackStarted(attackerSession, attackedMobile);
        combatSystem.defenseStarted(attackedSession, attackerMobile);
        assertEquals(ValuePair.of(attackerSession, attackedSession), combatSystem.getCombatSessions().get(KeyPair.of(attackerMobile, attackedMobile)));
    }

    @Test
    public void shouldRemoveSessionOnCombatFinished() {
        combatSystem.attackStarted(attackerSession, attackedMobile);
        combatSystem.defenseStarted(attackedSession, attackedMobile);
        combatSystem.combatFinished(attackerMobile, attackedMobile);
        assertFalse(combatSystem.getCombatSessions().containsKey(KeyPair.of(attackerMobile, attackedMobile)));
    }

    @Test
    public void shouldExecuteContainingSessions() {
        combatSystem.attackStarted(attackerSession, attackedMobile);
        combatSystem.defenseStarted(attackedSession, attackedMobile);

        when(physicalDamageCalculator.calculate(attackerMobile, attackedMobile)).thenReturn(1);

        combatSystem.execute();

        verify(attackerSession).fightOccurring(attackedMobile);
        verify(attackerSession).applyDamage(1);

        verify(attackedSession).fightOccurring(attackerMobile);
        verify(attackedSession).applyDamage(1);
    }
}