package net.sf.juoserver.model;

import lombok.ToString;
import net.sf.juoserver.api.*;

import java.util.*;

@ToString(onlyExplicitlyIncluded = true)
public class UOMobile implements Mobile {
    public static final int DEATH_MODEL_ID = 403;
    public static final int ALIVE_MODEL_ID = 0x190;
    @ToString.Include
    private int serialId;
    /**
     * The body type.
     */
    private int modelId = ALIVE_MODEL_ID; // 0x190 human - 0x3CA ghost
    @ToString.Include
    private String name;
    private int currentHitPoints;
    private int maxHitPoints;
    private boolean nameChangeFlag;
    private StatusFlag statusFlag;
    private SexRace sexRace;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int currentStamina;
    private int maxStamina;
    private int currentMana;
    private int maxMana;
    private int goldInPack;
    private int armorRating;
    private int weight;

    // statusFlag >= StatusFlag.UOML
    private int maxWeight;
    private RaceFlag raceFlag;

    private String title = "The Great";

    private boolean death;

    private Set<Skill> skills = new HashSet<Skill>(Arrays.asList(new Skill(Skills.Alchemy, 85, 80, 100, SkillLockFlag.Up),
            new Skill(Skills.Magery, 95, 90, 100, SkillLockFlag.Up),
            new Skill(Skills.Inscription, 95, 90, 100, SkillLockFlag.Up),
            new Skill(Skills.EvaluateIntelligence, 100, 100, 100, SkillLockFlag.Down),
            new Skill(Skills.ItemIdentify, 75, 70, 100, SkillLockFlag.Locked)));

    private Map<Layer, Item> items;
    private int hue = 0x83EA;
    private int x;
    private int y;
    private int z;
    private Direction direction = Direction.Southeast;
    private boolean running;
    private Notoriety notoriety = Notoriety.Innocent;
    private CharacterStatus characterStatus = CharacterStatus.Normal;
    private boolean npc;

    public UOMobile(int serialId, String playerName, int currentHitPoints,
                    int maxHitPoints, boolean nameChangeFlag, StatusFlag statusFlag,
                    SexRace sexRace, int strength, int dexterity, int intelligence,
                    int currentStamina, int maxStamina, int currentMana, int maxMana,
                    int goldInPack, int armorRating, int weight, int maxWeight,
                    RaceFlag raceFlag, Point3D position) {
        super();
        this.serialId = serialId;
        this.name = playerName;
        this.currentHitPoints = currentHitPoints;
        this.maxHitPoints = maxHitPoints;
        this.nameChangeFlag = nameChangeFlag;
        this.statusFlag = statusFlag;
        this.sexRace = sexRace;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.currentStamina = currentStamina;
        this.maxStamina = maxStamina;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.goldInPack = goldInPack;
        this.armorRating = armorRating;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.raceFlag = raceFlag;
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();

        items = new HashMap<Layer, Item>();
    }

    public UOMobile(int playerSerial, int modelId, String playerName, int currentHitPoints,
                    int maxHitPoints, boolean nameChangeFlag, StatusFlag statusFlag,
                    SexRace sexRace, int strength, int dexterity, int intelligence,
                    int currentStamina, int maxStamina, int currentMana, int maxMana,
                    int goldInPack, int armorRating, int weight, int maxWeight,
                    RaceFlag raceFlag, Point3D position, Notoriety notoriety, boolean npc) {
        this(playerSerial, playerName, currentHitPoints,
                maxHitPoints, nameChangeFlag, statusFlag,
                sexRace, strength, dexterity, intelligence,
                currentStamina, maxStamina, currentMana, maxMana,
                goldInPack, armorRating, weight, maxWeight,
                raceFlag, position);
        this.modelId = modelId;
        this.notoriety = notoriety;
        this.npc = npc;
    }

    public UOMobile(boolean npc) {
        this();
        this.npc = npc;
    }
    public UOMobile() {
        this.items = new HashMap<>();
    }

    @Override
    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    @Override
    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public void setLocation(Point3D location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCurrentHitPoints() {
        return currentHitPoints;
    }

    @Override
    public void setCurrentHitPoints(int currentHitPoints) {
        this.currentHitPoints = currentHitPoints;
    }

    @Override
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    @Override
    public boolean isNameChangeFlag() {
        return nameChangeFlag;
    }

    @Override
    public StatusFlag getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(StatusFlag statusFlag) {
        this.statusFlag = statusFlag;
    }

    @Override
    public SexRace getSexRace() {
        return sexRace;
    }

    public void setSexRace(SexRace sexRace) {
        this.sexRace = sexRace;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    @Override
    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public int getCurrentStamina() {
        return currentStamina;
    }

    @Override
    public int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    @Override
    public int getCurrentMana() {
        return currentMana;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public int getGoldInPack() {
        return goldInPack;
    }

    @Override
    public int getArmorRating() {
        return armorRating;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getMaxWeight() {
        return maxWeight;
    }

    @Override
    public RaceFlag getRaceFlag() {
        return raceFlag;
    }

    public void setRaceFlag(RaceFlag raceFlag) {
        this.raceFlag = raceFlag;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Set<Skill> getSkills() {
        return skills;
    }

    @Override
    public Map<Layer, Item> getItems() {
        return items;
    }

    @Override
    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    @Override
    public Notoriety getNotoriety() {
        return notoriety;
    }

    @Override
    public CharacterStatus getCharacterStatus() {
        return characterStatus;
    }

    /**
     * Updates this mobile's position according to their {@link #direction}.
     */
    @Override
    public void moveForward() {
        switch (direction) {
            case North:
                --y;
                break;
            case Northeast:
                ++x;
                --y;
                break;
            case East:
                ++x;
                break;
            case Southeast:
                ++x;
                ++y;
                break;
            case South:
                ++y;
                break;
            case Southwest:
                --x;
                ++y;
                break;
            case West:
                --x;
                break;
            case Northwest:
                --x;
                --y;
                break;
        }
    }

    @Override
    public String getPrefixNameSuffix() {
        return " \t" + getName() + "\t ";
    }

    @Override
    public byte getDirectionWithRunningInfo() {
        return (byte) (direction.getCode() | (isRunning() ? 0x80 : 0));
    }

    @Override
    public void setItemOnLayer(Layer layer, Item item) {
        items.put(layer, item);
    }

    @Override
    public Item getItemByLayer(Layer layer) {
        return items.get(layer);
    }

    @Override
    public boolean removeItem(Item item) {
        return items.remove(getLayer(item)) != null;
    }

    @Override
    public Layer getLayer(Item item) {
        for (Layer layer : Collections.unmodifiableSet(items.keySet())) {
            if (getItemByLayer(layer).equals(item)) {
                return layer;
            }
        }
        return null;
    }

    @Override
    public void emptyLayer(Layer layer) {
        removeItem(getItemByLayer(layer));
    }

    @Override
    public void setCharacterStatus(CharacterStatus characterStatus) {
        this.characterStatus = characterStatus;
    }

    @Override
    public int getWeaponBaseDamage() {
        if (items.containsKey(Layer.FirstValid)) {
            return items.get(Layer.FirstValid).baseDamage();
        }
        return 1;
    }

    @Override
    public Mobile location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public int distanceOf(Mobile mobile) {
        return (int) Math.hypot(x - mobile.getX(), y - mobile.getY());
    }

    @Override
    public void kill() {
        this.currentHitPoints = 0;
        this.currentMana = 0;
        this.currentStamina = 0;
        this.death = true;
        this.characterStatus = CharacterStatus.Normal;
        this.modelId = DEATH_MODEL_ID;
    }

    @Override
    public void revive() {
        this.currentHitPoints = 1;
        this.currentMana = 1;
        this.currentStamina = 1;
        this.modelId = ALIVE_MODEL_ID;
    }

    @Override
    public boolean isDeath() {
        return death;
    }

    @Override
    public boolean isNpc() {
        return npc;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + serialId;
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UOMobile other = (UOMobile) obj;
        if (serialId != other.serialId)
            return false;
        return true;
    }

}
