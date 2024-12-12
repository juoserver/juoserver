package net.sf.juoserver.model;

import net.sf.juoserver.api.Point3D;
import net.sf.juoserver.api.RaceFlag;
import net.sf.juoserver.api.SexRace;
import net.sf.juoserver.api.StatusFlag;

public class UOPlayer extends UOMobile {
    public UOPlayer(int playerSerial, String playerName, int currentHitPoints, int maxHitPoints, boolean nameChangeFlag, StatusFlag statusFlag, SexRace sexRace, int strength, int dexterity, int intelligence, int currentStamina, int maxStamina, int currentMana, int maxMana, int goldInPack, int armorRating, int weight, int maxWeight, RaceFlag raceFlag, Point3D position) {
        super(playerSerial, playerName, currentHitPoints, maxHitPoints, nameChangeFlag, statusFlag, sexRace, strength, dexterity, intelligence, currentStamina, maxStamina, currentMana, maxMana, goldInPack, armorRating, weight, maxWeight, raceFlag, position);
    }
}
