package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

public class ServerConfigurationImpl implements Configuration.ServerConfiguration {

    private int port;
    private String name;
    private String host;

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
