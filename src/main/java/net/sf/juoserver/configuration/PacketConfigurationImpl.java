package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

class PacketConfigurationImpl implements Configuration.PacketConfiguration {
    private boolean logging;
    @Override
    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }
}
