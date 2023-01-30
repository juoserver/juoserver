package net.sf.juoserver.api;

public interface CombatSystem extends SubSystem {

    void attackStarted(PlayerSession attackerSession, Mobile attacked);

    void defenseStarted(PlayerSession attackedSession, Mobile attacker);

    void combatFinished(Mobile attacker, Mobile attacked);
}
