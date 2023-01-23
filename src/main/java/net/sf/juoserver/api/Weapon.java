package net.sf.juoserver.api;

public enum Weapon {

    MAUL(AnimationType.ATTACK_WITH_SWORD_OVER_AND_SIDE),
    ;

    private final AnimationType animationType;

    Weapon(AnimationType animationType) {
        this.animationType = animationType;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }
}
