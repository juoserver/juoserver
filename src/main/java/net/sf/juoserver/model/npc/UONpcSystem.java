package net.sf.juoserver.model.npc;

import lombok.extern.slf4j.Slf4j;
import net.sf.juoserver.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Objects.requireNonNull;

@Slf4j
public class UONpcSystem implements NpcSystem, SubSystem, MobileListener {

    private final Core core;
    private final InterClientNetwork network;
    private final Configuration configuration;
    private final NpcSessionCycle sessionCycle;
    private final ExecutorService executorService;
    private final Map<NpcMobile, ContextBasedNpcSession> npcSessionMap = new HashMap<>();
    private final List<NpcSessionListener> sessionListeners = new ArrayList<>();

    public UONpcSystem(Core core, InterClientNetwork network, Configuration configuration, NpcSessionCycle sessionCycle, ExecutorService executorService) {
        this.core = requireNonNull(core, "Core must not be null");
        this.core.addMobileListener(this);
        this.network = network;
        this.configuration = configuration;
        this.sessionCycle = sessionCycle;
        this.executorService = executorService;
    }

    @Override
    public void execute(long uptime) {
        final var lock = new ReentrantLock();
        for (ContextBasedNpcSession session : npcSessionMap.values()) {
            if (lock.tryLock()) {
                try {
                    var npc = session.getMobile();
                    var aiScript = npc.getAIScript();

                    if (aiScript.isActive(session) && session.getContext() == null) {
                        session.setContext(new UONpcContext());

                        var future = CompletableFuture.runAsync(()-> sessionCycle.execute(aiScript, session), executorService);

                        future.whenComplete((a, throwable)-> {
                            if (throwable != null) {
                                log.error("Error to handle {} AI", npc, throwable);
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
            for (NpcSessionListener listener: sessionListeners) {
                listener.onSessionCreated(npc, session);
            }
        }
    }

    @Override
    public void onMobileRemoved(Mobile mobile) {
        if (mobile.isNpc()) {
            final var npc = (NpcMobile) mobile;
            final var session = npcSessionMap.get(npc);
            npcSessionMap.remove(npc);
            for (NpcSessionListener listener: sessionListeners) {
                listener.onSessionClosed(npc, session);
            }
        }
    }

    @Override
    public void addNpcSessionListener(NpcSessionListener listener) {
        this.sessionListeners.add(listener);
    }

    @Override
    public void removeNpcSessionListener(NpcSessionListener listener) {
        this.sessionListeners.remove(listener);
    }
}
