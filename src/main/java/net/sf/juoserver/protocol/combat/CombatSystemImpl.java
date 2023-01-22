package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.PlayerSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CombatSystemImpl implements CombatSystem {

    private final Map<KeyPair, ValuePair> combatSessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void attackStarted(PlayerSession attackerSession, Mobile attacked) {
        combatSessions.put(KeyPair.of(attackerSession.getMobile(), attacked), new ValuePair(attackerSession, null));
    }

    @Override
    public void defenseStarted(PlayerSession attackedSession, Mobile attacker) {
        var keyPair = KeyPair.of(attacker, attackedSession.getMobile());
        if (combatSessions.containsKey(keyPair)) {
            var attackerSession = combatSessions.get(keyPair).getAttackerSession();
            combatSessions.replace(keyPair, ValuePair.of(attackerSession, attackedSession));
        }
    }

    @Override
    public void combatFinished(Mobile attacker, Mobile attacked) {
        combatSessions.remove(new KeyPair(attacker, attacked));
    }

    @Override
    public void execute() {
        combatSessions.values().forEach(e->{
            var attacker = e.getAttackerSession();
            var attacked = e.getAttackedSession();

            if (getMobileDistance(attacker.getMobile(), attacked.getMobile()) <= 1) {
                attacker.fightOccurring(attacked.getMobile());
                attacked.fightOccurring(attacker.getMobile());

                // TODO calculate damage
                attacked.applyDamage(1);
            }
        });
    }

    public int getMobileDistance(Mobile attacker, Mobile attacked) {
        return (int) Math.hypot(attacker.getX() - attacked.getX(), attacker.getY() - attacked.getY());
    }

    protected Map<KeyPair, ValuePair> getCombatSessions() {
        return combatSessions;
    }

    protected static class KeyPair {
        private final Mobile attacker;
        private final Mobile attacked;

        public static KeyPair of(Mobile attacker, Mobile attacked) {
            return new KeyPair(attacker, attacked);
        }
        private KeyPair(Mobile attacker, Mobile attacked) {
            this.attacker = attacker;
            this.attacked = attacked;
        }

        public Mobile getAttacker() {
            return attacker;
        }

        public Mobile getAttacked() {
            return attacked;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeyPair keyPair = (KeyPair) o;
            return attacker.equals(keyPair.attacker) && attacked.equals(keyPair.attacked);
        }

        @Override
        public int hashCode() {
            return Objects.hash(attacker, attacked);
        }
    }

    protected static class ValuePair {
        private final PlayerSession attackerSession;
        private final PlayerSession attackedSession;

        public static ValuePair of(PlayerSession attackerSession, PlayerSession attackedSession) {
            return new ValuePair(attackerSession, attackedSession);
        }

        public ValuePair(PlayerSession attackerSession, PlayerSession attackedSession) {
            this.attackerSession = attackerSession;
            this.attackedSession = attackedSession;
        }

        public PlayerSession getAttackerSession() {
            return attackerSession;
        }

        public PlayerSession getAttackedSession() {
            return attackedSession;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ValuePair valuePair = (ValuePair) o;
            return attackerSession.equals(valuePair.attackerSession) && Objects.equals(attackedSession, valuePair.attackedSession);
        }

        @Override
        public int hashCode() {
            return Objects.hash(attackerSession, attackedSession);
        }
    }
}
