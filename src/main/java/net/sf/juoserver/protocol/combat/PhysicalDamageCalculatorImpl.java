package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Layer;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.PhysicalDamageCalculator;

public class PhysicalDamageCalculatorImpl implements PhysicalDamageCalculator {

    private static final int STRENGTH_DIVISOR = 10;
    private static final int DEXTERITY_DIVISOR = 50;

    @Override
    public int calculate(Mobile attacker, Mobile attacked) {
        var baseDamage = attacker.getItemByLayer(Layer.FirstValid).getBaseDamage();
        var armorRating = attacked.getArmorRating();

        // Strength Modifier
        var damageModifier = baseDamage + (attacker.getStrength() / STRENGTH_DIVISOR);
        var armorModifier = armorRating + (attacked.getStrength() / STRENGTH_DIVISOR);

        // Dexterity Modifier
        damageModifier = damageModifier + (attacker.getDexterity() / DEXTERITY_DIVISOR);
        armorModifier = armorModifier + (attacked.getDexterity() / DEXTERITY_DIVISOR);

        var damage = Math.floor(damageModifier / ((armorModifier + 100.0) / 100));

        return (int) damage;
    }

}
