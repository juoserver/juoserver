package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;

public class ConfigurationImpl implements Configuration {
    private String skillsIdxPath;
    private String mulPath;
    private boolean packetLoggingEnabled;
    private String commandActivationCharacter;
    private StatsConfiguration stats;
    private CombatConfiguration combat;
    private ServerConfiguration server;
    private FilesConfiguration files;

    @Override
    public String getSkillsIdxPath() {
        return skillsIdxPath;
    }

    @Override
    public String getMulPath() {
        return mulPath;
    }

    @Override
    public boolean isPacketLoggingEnabled() {
        return packetLoggingEnabled;
    }

    @Override
    public String getCommandActivationCharacter() {
        return commandActivationCharacter;
    }

    @Override
    public StatsConfiguration getStats() {
        return stats;
    }

    @Override
    public CombatConfiguration getCombat() {
        return combat;
    }

    @Override
    public ServerConfiguration getServer() {
        return server;
    }

    @Override
    public FilesConfiguration getFiles() {
        return files;
    }

    public void setSkillsIdxPath(String skillsIdxPath) {
        this.skillsIdxPath = skillsIdxPath;
    }

    public void setMulPath(String mulPath) {
        this.mulPath = mulPath;
    }

    public void setPacketLoggingEnabled(boolean packetLoggingEnabled) {
        this.packetLoggingEnabled = packetLoggingEnabled;
    }

    public void setCommandActivationCharacter(String commandActivationCharacter) {
        this.commandActivationCharacter = commandActivationCharacter;
    }

    public void setStats(StatsConfiguration stats) {
        this.stats = stats;
    }

    public void setCombat(CombatConfiguration combat) {
        this.combat = combat;
    }

    public void setServer(ServerConfiguration server) {
        this.server = server;
    }

    public void setFiles(FilesConfiguration files) {
        this.files = files;
    }
}
