package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.PlayerSession;
import net.sf.juoserver.api.SubSystem;

public interface CombatSystem extends SubSystem {

    int calculateAttackedDamage(Mobile attacker, Mobile attacked);

    boolean isOnRangeOfDamage(Mobile attacker, Mobile attacked);


    void startedAttack(PlayerSession attackerSession, Mobile attacked);

    void startedDefense(PlayerSession attackedSession, Mobile attacker);

    void combatFinished(Mobile attacker, Mobile attacked);
}
