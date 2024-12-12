package net.sf.juoserver.api;

public interface CombatSession {

    /**
     * Apply a damage to the mobile
     * @param damage Amount of damage to be received
     */
    void receiveDamage(int damage);

    /**
     * Will attack with the equipped weapon
     */
    void attackWithDamage(Mobile opponent, int damage);
}
