package net.sf.juoserver.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.protocol.GameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private static final JavaPropsSchema JAVA_PROPS_SCHEMA = new JavaPropsSchema().withPathSeparator(".");
    private final JavaPropsMapper propsMapper;
    private final YAMLMapper yamlMapper;

    private ConfigurationFactory() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addAbstractTypeMapping(Configuration.class, ConfigurationImpl.class);
        simpleModule.addAbstractTypeMapping(Configuration.StatsConfiguration.class, StatsConfigurationImpl.class);
        simpleModule.addAbstractTypeMapping(Configuration.CombatConfiguration.class, CombatConfigurationImpl.class);
        simpleModule.addAbstractTypeMapping(Configuration.ServerConfiguration.class, ServerConfigurationImpl.class);
        simpleModule.addAbstractTypeMapping(Configuration.FilesConfiguration.class, FilesConfigurationImpl.class);
        simpleModule.addAbstractTypeMapping(Configuration.CommandConfiguration.class, CommandConfigurationImpl.class);
        simpleModule.addAbstractTypeMapping(Configuration.PacketConfiguration.class, PacketConfigurationImpl.class);
        simpleModule.addAbstractTypeMapping(Configuration.ClientConfiguration.class, ClientConfigurationImpl.class);

        this.propsMapper = new JavaPropsMapper();
        this.propsMapper.registerModule(simpleModule);
        this.propsMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.yamlMapper = new YAMLMapper();
        this.yamlMapper.registerModule(simpleModule);
        this.yamlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ConfigurationFactory newInstance() {
        return new ConfigurationFactory();
    }

    public Configuration newConfiguration() {

        try {
            var configMap = new HashMap<>(yamlToMap(getClass().getResource("/configuration.yaml")));

            var yamlPath = Path.of("configuration.yaml");
            if (Files.exists(yamlPath)) {
                configMap.putAll(yamlToMap(yamlPath.toUri().toURL()));
            }

            var propertyPath = Path.of("configuration.properties");
            if (Files.exists(propertyPath)) {
                configMap.putAll(propertiesToMap(propertyPath.toUri().toURL()));
            }

            configMap.putAll(systemEnvToMap());

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Final configuration MAP: {}", configMap);
            }
            return propsMapper.readMapAs(configMap, JAVA_PROPS_SCHEMA, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> yamlToMap(URL url) throws IOException {
        var yamlConfiguration = yamlMapper.readValue(url, Configuration.class);

        return propsMapper.writeValueAsMap(yamlConfiguration, JAVA_PROPS_SCHEMA);
    }

    private Map<String, String> propertiesToMap(URL url) throws IOException {
        var properties = new Properties();
        try (InputStream inputStream = url.openStream()) {
            properties.load(inputStream);
        }

        var propertiesConfiguration = propsMapper.readPropertiesAs(properties, Configuration.class);
        return propsMapper.writeValueAsMap(propertiesConfiguration, JAVA_PROPS_SCHEMA);
    }

    private Map<String, String> systemEnvToMap() throws IOException {
        var configuration = propsMapper.readEnvVariablesAs(JAVA_PROPS_SCHEMA, Configuration.class);
        return propsMapper.writeValueAsMap(configuration, JAVA_PROPS_SCHEMA);
    }
}
