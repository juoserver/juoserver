package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

public class StatsConfigurationImpl implements Configuration.StatsConfiguration {

    private int lifeLimit;

    @Override
    public int getLifeLimit() {
        return lifeLimit;
    }

    public void setLifeLimit(int lifeLimit) {
        this.lifeLimit = lifeLimit;
    }
}
