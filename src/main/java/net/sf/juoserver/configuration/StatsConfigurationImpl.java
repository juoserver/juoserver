package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

class StatsConfigurationImpl implements Configuration.StatsConfiguration {

    private int maxHitPoints;

    private int maxStamina;

    private int maxMana;
    @Override
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    @Override
    public int getMaxStamina() {
        return maxStamina;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
}
