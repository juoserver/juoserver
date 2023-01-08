package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;

public interface CombatSystem {

    int calculateAttackedDamage(Mobile attacker, Mobile attacked);

    boolean isOnRangeOfCombat(Mobile attacker, Mobile attacked);
}
