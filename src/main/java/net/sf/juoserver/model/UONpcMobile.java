package net.sf.juoserver.model;

import net.sf.juoserver.api.*;

public class UONpcMobile extends UOMobile implements NpcMobile {

    private final AIScript aiScript;

    public UONpcMobile(int serialId, String name, Point3D location, AIScript aiScript) {
        super(serialId,  0x9,name, 100, 100, false, StatusFlag.UOML, SexRace.MaleHuman, 100,100,100,1000,100,100,100,0,0,0,100,RaceFlag.Elf, location, Notoriety.Murderer, true);
        this.aiScript = aiScript;
    }

    @Override
    public AIScript getAIScript() {
        return aiScript;
    }
}
