package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;

public class CombatSystemImpl implements CombatSystem {
    @Override
    public int calculateAttackedDamage(Mobile attacker, Mobile attacked) {
        // TODO calculate the damage based on attributes
        return 1;
    }

    @Override
    public boolean isOnRangeOfCombat(Mobile attacker, Mobile attacked) {
        int distance = (int) Math.hypot(attacker.getX()-attacked.getX(), attacker.getY()-attacked.getY());
        // TODO verify equipped weapon
        return distance < 2;
    }
}
