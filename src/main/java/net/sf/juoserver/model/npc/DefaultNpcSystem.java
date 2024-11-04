package net.sf.juoserver.model.npc;

import net.sf.juoserver.api.*;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultNpcSystem implements NpcSystem, SubSystem {

    private final Core core;
    private final InterClientNetwork network;
    private final Configuration configuration;
    private final Map<Mobile, Set<Mobile>> mobilesInRangeOfNpc = new HashMap<>();
    private final Map<Mobile, Set<Mobile>> npcsInRangeOfMobile = new HashMap<>();

    public DefaultNpcSystem(Core core, InterClientNetwork network, Configuration configuration) {
        this.core = core;
        this.network = network;
        this.configuration = configuration;
    }

    @Override
    public void mobileMoved(Mobile mobile) {
        final int los = configuration.getClient().getLos();

        var npcs = core.findNpcInRange(mobile, los);

        if (npcsInRangeOfMobile.containsKey(mobile)) {
            var noLongerInRange = npcsInRangeOfMobile.get(mobile)
                    .stream()
                    .filter(npcs::contains)
                    .collect(Collectors.toSet());

        }



        for (Mobile npcMobile : npcs) {
            mobilesInRangeOfNpc.computeIfAbsent(npcMobile, (key)->{
                var list = new HashSet<Mobile>();
                list.add(mobile);
                return list;
            });
            mobilesInRangeOfNpc.computeIfPresent(npcMobile, (key, value)->{
                value.add(mobile);
                return value;
            });

            npcsInRangeOfMobile.computeIfAbsent(mobile, (key)->{
                var list = new HashSet<Mobile>();
                list.add(npcMobile);
                return list;
            });

            npcsInRangeOfMobile.computeIfPresent(mobile, (key,value)->{
                value.add(npcMobile);
                return value;
            });
        }

    }

    @Override
    public void execute(long uptime) {
        // TODO Execute NPC AI
        for (Mobile npc : mobilesInRangeOfNpc.keySet()) {
            System.out.println("tem gente perto "+npc);
        }
    }
}
