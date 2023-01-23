package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.TestingFactory;
import net.sf.juoserver.api.Mobile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalDamageCalculatorImplTest {
    public PhysicalDamageCalculatorImpl physicalDamageCalculator;
    private Mobile attacker;
    private Mobile attacked;

    @BeforeEach
    public void setUp() {
        physicalDamageCalculator = new PhysicalDamageCalculatorImpl();
        attacker = TestingFactory.createTestMobile(1, "Asder");
        attacked = TestingFactory.createTestMobile(2, "Loller");

    }

    @Test
    public void shouldCalculateDamageGivenMobiles() {
        var damage = physicalDamageCalculator.calculate(attacker, attacked);
    }
}