package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.PlayerSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CombatSystemImpl implements CombatSystem {

    private final Map<KeyPair, ValuePair> combatSessionsMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public int calculateAttackedDamage(Mobile attacker, Mobile attacked) {
        // TODO calculate the damage based on attributes
        return 1;
    }

    @Override
    public boolean isOnRangeOfDamage(Mobile attacker, Mobile attacked) {
        int distance = (int) Math.hypot(attacker.getX()-attacked.getX(), attacker.getY()-attacked.getY());
        // TODO verify equipped weapon
        return distance < 2;
    }

    @Override
    public void startedAttack(PlayerSession attackerSession, Mobile attacked) {
        combatSessionsMap.put(new KeyPair(attackerSession.getMobile(), attacked), new ValuePair(attackerSession));
    }

    @Override
    public void startedDefense(PlayerSession attackedSession, Mobile attacker) {
        System.out.println(attackedSession.getMobile()+"  "+attacker+ "   "+combatSessionsMap);
        combatSessionsMap.get(new KeyPair(attacker, attackedSession.getMobile())).setAttackedSession(attackedSession);
    }

    @Override
    public void combatFinished(Mobile attacker, Mobile attacked) {
        combatSessionsMap.remove(new KeyPair(attacker, attacked));
    }

    @Override
    public void execute() {
        combatSessionsMap.values().forEach(e->{
            System.out.println(e.getAttackedSession()+" "+e.getAttackerSession());
        });
    }

    private static class KeyPair {
        private final Mobile attacker;
        private final Mobile attacked;;

        public KeyPair(Mobile attacker, Mobile attacked) {
            this.attacker = attacker;
            this.attacked = attacked;
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

    private static class ValuePair {
        private final PlayerSession attackerSession;
        private PlayerSession attackedSession;

        public ValuePair(PlayerSession attackerSession) {
            this.attackerSession = attackerSession;
        }

        public PlayerSession getAttackerSession() {
            return attackerSession;
        }

        public PlayerSession getAttackedSession() {
            return attackedSession;
        }

        public void setAttackedSession(PlayerSession attackedSession) {
            this.attackedSession = attackedSession;
        }
    }
}
