package net.sf.juoserver.model;

import net.sf.juoserver.api.*;

public class UONpc extends UOMobile {
    public UONpc(int serialId, String name, Point3D location) {
        super(serialId,  0x9,name, 100, 100, false, StatusFlag.UOML, SexRace.MaleHuman, 100,100,100,1000,100,100,100,0,0,0,100,RaceFlag.Elf, location, Notoriety.Murderer, true);
    }
}
