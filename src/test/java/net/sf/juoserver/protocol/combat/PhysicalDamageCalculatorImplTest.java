package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.api.Mobile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhysicalDamageCalculatorImplTest {
    public PhysicalDamageCalculatorImpl physicalDamageCalculator;
    @Mock
    public Configuration configuration;
    @Mock
    public Configuration.CombatConfiguration combatConfiguration;
    @Mock
    public Configuration.StatsConfiguration statsConfiguration;

    @BeforeEach
    public void setUp() {
        lenient().when(configuration.getCombat()).thenReturn(combatConfiguration);
        lenient().when(configuration.getStats()).thenReturn(statsConfiguration);

        lenient().when(combatConfiguration.getDexAttackDivisorModifier()).thenReturn(5);
        lenient().when(combatConfiguration.getStrAttackDivisorModifier()).thenReturn(2);
        lenient().when(combatConfiguration.getDexDefenseDivisorModifier()).thenReturn(5);
        lenient().when(combatConfiguration.getStrDefenseDivisorModifier()).thenReturn(2);

        lenient().when(statsConfiguration.getMaxHitPoints()).thenReturn(100);

        physicalDamageCalculator = new PhysicalDamageCalculatorImpl(configuration);
    }

    @DisplayName("Should calculate damage with similar modifiers")
    @Test
    public void shouldCalculateDamageWithSimilarModifiers() {
        var attacker =  givenAttacker(5, 10,10);
        var attacked = givenAttacked(5,10,10);
        var damage = physicalDamageCalculator.calculate(attacker, attacked);
        assertEquals(5, damage);
    }

    @DisplayName("Should calculate damage with attacker having superior modifiers")
    @Test
    public void shouldCalculateDamageWithAttackerSuperiorModifiers() {
        var damage = physicalDamageCalculator.calculate(givenAttacker(100, 100,100), givenAttacked(100,50,50));
        assertEquals(37, damage);
    }

    @DisplayName("Should calculate damage with attacker having inferior modifiers")
    @Test
    public void shouldCalculateDamageWithAttackerInferiorModifiers() {
        var damage = physicalDamageCalculator.calculate(givenAttacker(100, 50,50), givenAttacked(100,100,100));
        assertEquals(26, damage);
    }

    public Mobile givenAttacker(int baseDamage, int strength, int dexterity) {
        var attacker = mock(Mobile.class);
        when(attacker.getWeaponBaseDamage()).thenReturn(baseDamage);
        when(attacker.getStrength()).thenReturn(strength);
        when(attacker.getDexterity()).thenReturn(dexterity);
        return attacker;
    }

    public Mobile givenAttacked(int baseArmor, int strength, int dexterity) {
        var attacked = mock(Mobile.class);
        when(attacked.getArmorRating()).thenReturn(baseArmor);
        when(attacked.getStrength()).thenReturn(strength);
        when(attacked.getDexterity()).thenReturn(dexterity);
        return attacked;
    }
}