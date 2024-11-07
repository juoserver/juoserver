package net.sf.juoserver.model.npc;

import net.sf.juoserver.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

public class UONpcSystem implements NpcSystem, SubSystem, MobileListener {

    private final Core core;
    private final InterClientNetwork network;
    private final Configuration configuration;
    private final Map<Npc, UONpcSession> npcSessionMap = new HashMap<>();

    public UONpcSystem(Core core, InterClientNetwork network, Configuration configuration) {
        this.core = core;
        this.network = network;
        this.configuration = configuration;
    }

    @Override
    public void execute(long uptime) {
        final var lock = new ReentrantLock();
        for (UONpcSession session : npcSessionMap.values()) {
            if (lock.tryLock()) {
                try {
                    var npc = session.getNpc();
                    var aiScript = npc.getAIScript();
                    if (aiScript.isActivated(session) && !session.isActive()) {
                        session.setActive(true);
                        CompletableFuture.runAsync(()->aiScript.execute(session))
                                .whenComplete((a,e)-> session.setActive(false));
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public void onMobileCreated(Mobile mobile) {
        if (mobile.isNpc()) {
            var npc = (Npc) mobile;
            var session = new UONpcSession(npc, network);
            npcSessionMap.put(npc, session);
        }
    }

    @Override
    public void onMobileRemoved(Mobile mobile) {
        npcSessionMap.remove((Npc) mobile);
    }
}
