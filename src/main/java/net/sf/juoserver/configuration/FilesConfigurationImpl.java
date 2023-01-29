package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

public class FilesConfigurationImpl implements Configuration.FilesConfiguration {

    private String mulPath;

    @Override
    public String getMulPath() {
        return mulPath;
    }

    public void setMulPath(String mulsPath) {
        this.mulPath = mulsPath;
    }
}
