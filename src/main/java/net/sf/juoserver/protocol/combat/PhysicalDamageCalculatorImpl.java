package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.PhysicalDamageCalculator;

public class PhysicalDamageCalculatorImpl implements PhysicalDamageCalculator {

    private final Configuration configuration;

    private final double normalizer;

    public PhysicalDamageCalculatorImpl(Configuration configuration) {
        this.configuration = configuration;
        var lifeLimit = configuration.getStats().getMaxHitPoints();
        normalizer = lifeLimit / (lifeLimit + (0.9 * lifeLimit));
    }

    @Override
    public int calculate(Mobile attacker, Mobile attacked) {
        var combatConfiguration = configuration.getCombat();
        var baseDamage = attacker.getWeaponBaseDamage();
        var armorRating = attacked.getArmorRating();

        // Strength Modifier
        // Attack
        var damageModifier = baseDamage + (attacker.getStrength() / combatConfiguration.getStrAttackDivisorModifier());
        // Defense
        var armorModifier = armorRating + (attacked.getStrength() / combatConfiguration.getStrDefenseDivisorModifier());

        // Dexterity Modifier
        // Attack
        damageModifier = damageModifier + (attacker.getDexterity() / combatConfiguration.getDexAttackDivisorModifier());
        // Defense
        armorModifier = armorModifier + (attacked.getDexterity() / combatConfiguration.getDexDefenseDivisorModifier());

        var damage = Math.floor(damageModifier / ((armorModifier + 100.0) / 100));

        return (int) (damage * normalizer);
    }

}
