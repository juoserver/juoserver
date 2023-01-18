package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;

public interface CombatSystem {

    int calculateAttackedDamage(Mobile attacker, Mobile attacked);

    boolean isOnRangeOfDamage(Mobile attacker, Mobile attacked);

    void beginCombat(Mobile attacker, Mobile attacked);

    void endCombat(Mobile attacker, Mobile attacked);


}
