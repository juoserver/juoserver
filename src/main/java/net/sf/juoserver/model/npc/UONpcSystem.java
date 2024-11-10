package net.sf.juoserver.model.npc;

import lombok.extern.slf4j.Slf4j;
import net.sf.juoserver.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class UONpcSystem implements NpcSystem, SubSystem, MobileListener {

    private final Core core;
    private final InterClientNetwork network;
    private final Configuration configuration;
    private final Map<NpcMobile, UONpcSession> npcSessionMap = new HashMap<>();

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
                    var npc = session.getMobile();
                    var aiScript = npc.getAIScript();

                    if (aiScript.isActive(session) && session.getContext() == null) {
                        session.setContext(new ActiveNpcContext());
                        CompletableFuture.runAsync(()->{
                            while (aiScript.isActive(session) && !npc.isDeath()) {
                                aiScript.execute(session.getContext(), session);
                                try {
                                    Thread.sleep(400); // TODO delay based on mobile status
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }).whenComplete((a,e)-> {
                            if (e != null) {
                                log.error("Error to handle {} AI", npc, e);
                            }
                            session.setContext(null);
                        });
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
            var npc = (NpcMobile) mobile;
            var session = new UONpcSession(npc, network, core);
            npcSessionMap.put(npc, session);
        }
    }

    @Override
    public void onMobileRemoved(Mobile mobile) {
        npcSessionMap.remove((NpcMobile) mobile);
    }
}
