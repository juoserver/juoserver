package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

class CommandConfigurationImpl implements Configuration.CommandConfiguration {
    private String activationChar;
    @Override
    public String getActivationChar() {
        return activationChar;
    }

    public void setActivationChar(String activationChar) {
        this.activationChar = activationChar;
    }
}
