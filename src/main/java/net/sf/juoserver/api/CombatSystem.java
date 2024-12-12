package net.sf.juoserver.api;

public interface CombatSystem {

    void beginCombat(Mobile attacker, Mobile attacked);

    void attackStarted(PlayerSession attackerSession, Mobile attacked);

    void defenseStarted(PlayerSession attackedSession, Mobile attacker);

    void combatFinished(Mobile attacker, Mobile attacked);

    /**
     * It iterate over all combat sessions for the mobile killed and close
     * @param mobile mobile Killed
     */
    void mobileKilled(Mobile mobile);

    void registerMobile(Mobile mobile, CombatSession combatSession);

    void removeMobile(Mobile mobile);
}
