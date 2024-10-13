package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.PlayerSession;

import java.util.Objects;

record SessionGroup(CombatSession combatSession, PlayerSession attackerSession, PlayerSession attackedSession) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionGroup sessionGroup = (SessionGroup) o;
        return attackerSession.equals(sessionGroup.attackerSession) && Objects.equals(attackedSession, sessionGroup.attackedSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackerSession, attackedSession);
    }
}
