package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

public class ClientConfigurationImpl implements Configuration.ClientConfiguration {

    private int los;

    @Override
    public int getLos() {
        return los;
    }

    public void setLos(int los) {
        this.los = los;
    }
}
