package net.sf.juoserver.model.npc;

import lombok.RequiredArgsConstructor;
import net.sf.juoserver.api.AIScript;
import net.sf.juoserver.api.NpcSessionCycle;

@RequiredArgsConstructor
public class UONpcSessionCycle implements NpcSessionCycle {

    /**
     * Decreasing of delay as much dexterity increase
     */
    private static final int SLOPE = -3;
    /**
     * Delay value when dexterity is zero
     */
    private static final int INTERCEPT = 500;

    @Override
    public void execute(AIScript script, ContextBasedNpcSession session) {
        while (script.isActive(session) && !session.getMobile().isDeath()) {
            try {
                var dexterity = session.getMobile().getDexterity();
                script.execute(session.getContext(), session);

                var delay = SLOPE * dexterity + INTERCEPT;

                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
