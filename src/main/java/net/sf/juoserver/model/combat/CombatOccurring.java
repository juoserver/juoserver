package net.sf.juoserver.model.combat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.sf.juoserver.api.Mobile;

@Getter
@RequiredArgsConstructor
public class CombatOccurring {
    private final Mobile mobile1;
    private final Mobile mobile2;
    private long combatLoop = 1;

    public void resetCombatLoop() {
        this.combatLoop = 1;
    }

    public void incrementCombatLoop() {
        this.combatLoop++;
    }
}
