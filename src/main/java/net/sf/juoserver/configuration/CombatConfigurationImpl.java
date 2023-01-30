package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

class CombatConfigurationImpl implements Configuration.CombatConfiguration {
    private int dexAttackDivisorModifier;
    private int strAttackDivisorModifier;
    private int dexDefenseDivisorModifier;
    private int strDefenseDivisorModifier;

    @Override
    public int getDexAttackDivisorModifier() {
        return dexAttackDivisorModifier;
    }

    @Override
    public int getStrAttackDivisorModifier() {
        return strAttackDivisorModifier;
    }

    @Override
    public int getDexDefenseDivisorModifier() {
        return dexDefenseDivisorModifier;
    }

    @Override
    public int getStrDefenseDivisorModifier() {
        return strDefenseDivisorModifier;
    }

    public void setDexAttackDivisorModifier(int dexAttackDivisorModifier) {
        this.dexAttackDivisorModifier = dexAttackDivisorModifier;
    }

    public void setStrAttackDivisorModifier(int strAttackDivisorModifier) {
        this.strAttackDivisorModifier = strAttackDivisorModifier;
    }

    public void setDexDefenseDivisorModifier(int dexDefenseDivisorModifier) {
        this.dexDefenseDivisorModifier = dexDefenseDivisorModifier;
    }

    public void setStrDefenseDivisorModifier(int strDefenseDivisorModifier) {
        this.strDefenseDivisorModifier = strDefenseDivisorModifier;
    }
}
