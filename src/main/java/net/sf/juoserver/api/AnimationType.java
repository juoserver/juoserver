package net.sf.juoserver.api;

public enum AnimationType implements Coded {
    WALK_UNARMED(0x00),
    WALK_ARMED(0x01),
    RUN_UNARMED(0x02),
    RUN_ARMED(0x03),
    STAND(0x04),
    SHIFT_SHOULDERS(0x05),
    HANDS_ON_HIPS(0x06),
    ATTACK_STANCE_SHORT(0x07),
    ATTACK_STANCE_LONGER(0x08),
    ATTACK_WITH_KNIFE(0x09),
    UNDERHANDED(0x0a),
    ATTACK_OVERHAND_WITH_SWORD(0x0b),
    ATTACK_WITH_SWORD_OVER_AND_SIDE(0x0c),
    ATTACK_WITH_SWORD_SIDE(0x0d),
    STAB_WITH_POINT_OF_SWORD(0x0e),
    READY_STANCE(0x0f),
    MAGIC_BUTTER_CHUM(0x10),
    HANDS_OVER_HEAD(0x11),
    BOW_SHOT(0x12),
    CROSSBOW(0x13),
    GET_HIT(0x14),
    FALL_DOWN_AND_DIE_BACKWARDS(0x15),
    FALL_DOWN_AND_DIE_FORWARDS(0x16),
    RIDE_HORSE_LONG(0x17),
    RIDE_HORSE_MEDIUM(0x18),
    RIDE_HORSE_SHORT(0x19),
    SWING_SWORD_FROM_HORSE(0x1a),
    NORMAL_BOW_SHOT_ON_HORSE(0x1b),
    CROSSBOW_SHOT(0x1c),
    BLOCK_ON_HORSE_WITH_SHIELD(0x1d),
    BLOCK_ON_GROUND_WITH_SHIELD(0x1e),
    SWING_GET_HIT_MIDDLE(0x1f),
    BOW_DEEP(0x20),
    SALUTE(0x21),
    SCRACTH_HEAD(0x22),
    ONE_FOOT_FORWARD_FOR_TWO_SECS(0x23),
    SAME(0x24);

    private final int code;

    AnimationType(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
