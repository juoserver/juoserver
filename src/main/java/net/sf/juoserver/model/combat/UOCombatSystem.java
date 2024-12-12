package net.sf.juoserver.model.combat;

import net.sf.juoserver.api.*;

import java.util.*;

public class UOCombatSystem implements CombatSystem, SubSystem {
    private final PhysicalDamageCalculator physicalDamageCalculator;
    private final Map<Mobile, CombatSession> sessionMap = new Hashtable<>();
    private final List<CombatOccurring> combatsOccurring = new ArrayList<>();

    public UOCombatSystem(PhysicalDamageCalculator physicalDamageCalculator) {
        this.physicalDamageCalculator = physicalDamageCalculator;
    }

    @Override
    public void beginCombat(Mobile attacker, Mobile attacked) {
        combatsOccurring.add(new CombatOccurring(attacker, attacked));
    }

    @Override
    public void attackStarted(PlayerSession attackerSession, Mobile attacked) {
        //combatSessions.put(KeyPair.of(attackerSession.getMobile(), attacked), new SessionGroup(new CombatSession(System.nanoTime()), attackerSession, null));
    }

    @Override
    public void defenseStarted(PlayerSession attackedSession, Mobile attacker) {

    }

    @Override
    public void combatFinished(Mobile attacker, Mobile attacked) {

    }

    @Override
    public void mobileKilled(Mobile mobile) {

    }

    @Override
    public void execute(long uptime) {

        var finished = new LinkedList<CombatOccurring>();

        for (CombatOccurring combat : combatsOccurring) {
            final var mobile1 = combat.getMobile1();
            final var mobile2 = combat.getMobile2();

            // TODO check equiped weapon distance
            if (mobile1.distanceOf(mobile2) <= 1) {
                final var loop = combat.getCombatLoop();
                if (loop % 3 == 0) {
                    final var mobile1Damage = physicalDamageCalculator.calculate(mobile2, mobile1);
                    final var mobile2Damage = physicalDamageCalculator.calculate(mobile1, mobile2);

                    var mobile1Session = sessionMap.get(mobile1);
                    var mobile2Session = sessionMap.get(mobile2);

                    if (mobile2Damage < mobile1Damage) {
                        mobile2Session.attackWithDamage(mobile1, mobile2Damage);
                        mobile1Session.receiveDamage(mobile1Damage);
                    } else {
                        mobile1Session.attackWithDamage(mobile2, mobile1Damage);
                        mobile2Session.receiveDamage(mobile2Damage);
                    }

                    if (mobile1.isDeath()) {
                        sessionMap.remove(mobile1);
                        finished.add(combat);
                    }
                    if (mobile2.isDeath()) {
                        sessionMap.remove(mobile2);
                        finished.add(combat);
                    }

                    combat.resetCombatLoop();
                } else {
                    combat.incrementCombatLoop();
                }
            }
        }

        combatsOccurring.removeAll(finished);
    }

    @Override
    public void registerMobile(Mobile mobile, CombatSession combatSession) {
        this.sessionMap.put(mobile, combatSession);
    }

    @Override
    public void removeMobile(Mobile mobile) {
        System.out.println("removido "+mobile);
        this.sessionMap.remove(mobile);
    }
}
