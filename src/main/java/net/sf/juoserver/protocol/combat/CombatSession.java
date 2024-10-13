package net.sf.juoserver.protocol.combat;

public class CombatSession {

    private final long startTime;
    private long combatLoop;

    public CombatSession(long startTime) {
        this.startTime = startTime;
        this.combatLoop = 1;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCombatLoop() {
        return combatLoop;
    }

    public void updateCombatLoop() {
        this.combatLoop++;
    }

    public void resetCombatLoop() {
        this.combatLoop = 1;
    }
}
