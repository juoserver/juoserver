package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;

public class CombatSystemImpl implements CombatSystem {

    private Thread combatThread;

    @Override
    public int calculateAttackedDamage(Mobile attacker, Mobile attacked) {
        // TODO calculate the damage based on attributes
        return 1;
    }

    @Override
    public boolean isOnRangeOfDamage(Mobile attacker, Mobile attacked) {
        int distance = (int) Math.hypot(attacker.getX()-attacked.getX(), attacker.getY()-attacked.getY());
        // TODO verify equipped weapon
        return distance < 2;
    }

    @Override
    public void beginCombat(Mobile attacker, Mobile attacked) {

    }

    @Override
    public void endCombat(Mobile attacker, Mobile attacked) {

    }

}
