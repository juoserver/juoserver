package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.*;

import java.util.HashMap;
import java.util.Map;

public class CombatSystemImpl implements CombatSystem, SubSystem {

    private final Map<KeyPair, SessionGroup> combatSessions = new HashMap<>();
    private final PhysicalDamageCalculator physicalDamageCalculator;

    public CombatSystemImpl(PhysicalDamageCalculator physicalDamageCalculator) {
        this.physicalDamageCalculator = physicalDamageCalculator;
    }

    @Override
    public void attackStarted(PlayerSession attackerSession, Mobile attacked) {
        combatSessions.put(KeyPair.of(attackerSession.getMobile(), attacked), new SessionGroup(new CombatSession(System.nanoTime()), attackerSession, null));
    }

    @Override
    public void defenseStarted(PlayerSession attackedSession, Mobile attacker) {
        var keyPair = KeyPair.of(attacker, attackedSession.getMobile());
        if (combatSessions.containsKey(keyPair)) {
            var sessionGroup = combatSessions.get(keyPair);
            combatSessions.replace(keyPair, new SessionGroup(sessionGroup.combatSession(), sessionGroup.attackerSession(), attackedSession));
        }
    }

    @Override
    public void combatFinished(Mobile attacker, Mobile attacked) {
        combatSessions.remove(new KeyPair(attacker, attacked));
    }

    @Override
    public void mobileKilled(Mobile mobile) {
        var iterator = combatSessions.keySet().iterator();
        while (iterator.hasNext()) {
            var pair = iterator.next();
            if (pair.attacker().equals(mobile) || pair.attacked().equals(mobile)) {
                combatSessions.remove(pair);
            }
        }
    }

    @Override
    public void execute(long uptime) {
        combatSessions.values().forEach(entry->{
            var combatSession = entry.combatSession();
            var combatLoop = combatSession.getCombatLoop();

            var attackerSession = entry.attackerSession();
            var attackerMobile = attackerSession.getMobile();

            var attackedSession = entry.attackedSession();
            var attackedMobile = attackedSession.getMobile();

            if (attackerMobile.distanceOf(attackedMobile) <= 1) {
                if (combatLoop % 3 == 0) {
                    final var attackerReceivedDamage = physicalDamageCalculator.calculate(attackedMobile, attackerMobile);
                    final var attackedReceivedDamage = physicalDamageCalculator.calculate(attackerMobile, attackedMobile);
                    attackerSession.applyDamage(attackerReceivedDamage, attackedMobile);
                    attackedSession.applyDamage(attackedReceivedDamage, attackerMobile);
                    combatSession.resetCombatLoop();
                } else {
                    combatSession.updateCombatLoop();
                }
            }
        });
    }

}
