package net.sf.juoserver.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import net.sf.juoserver.api.ConfigFileReader;
import net.sf.juoserver.api.NpcMobile;
import net.sf.juoserver.model.UONpcMobile;

import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class YamlConfigFileReader implements ConfigFileReader {
    private final YAMLMapper yamlMapper;

    public YamlConfigFileReader() {
        this.yamlMapper = new YAMLMapper();
    }
    @Override
    public List<NpcMobile> loadNpcs() {
        try (Reader reader = new FileReader("config/npcs.yaml")) {
            return Arrays.asList(yamlMapper.readValue(reader, UONpcMobile[].class));
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
