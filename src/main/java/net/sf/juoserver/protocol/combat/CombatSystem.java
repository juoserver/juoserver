package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.PlayerSession;
import net.sf.juoserver.api.SubSystem;

public interface CombatSystem extends SubSystem {

    void attackStarted(PlayerSession attackerSession, Mobile attacked);

    void defenseStarted(PlayerSession attackedSession, Mobile attacker);

    void combatFinished(Mobile attacker, Mobile attacked);
}
