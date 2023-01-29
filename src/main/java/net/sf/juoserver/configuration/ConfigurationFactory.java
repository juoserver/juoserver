package net.sf.juoserver.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import net.sf.juoserver.api.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationFactory {

    private static final JavaPropsSchema JAVA_PROPS_SCHEMA = new JavaPropsSchema().withPathSeparator(".");
    private final SimpleModule simpleModule;
    private final JavaPropsMapper propsMapper;
    private final YAMLMapper yamlMapper;

    private ConfigurationFactory() {
        this.simpleModule = new SimpleModule();
        this.simpleModule.addAbstractTypeMapping(Configuration.class, ConfigurationImpl.class);
        this.simpleModule.addAbstractTypeMapping(Configuration.StatsConfiguration.class, StatsConfigurationImpl.class);
        this.simpleModule.addAbstractTypeMapping(Configuration.CombatConfiguration.class, CombatConfigurationImpl.class);
        this.simpleModule.addAbstractTypeMapping(Configuration.ServerConfiguration.class, ServerConfigurationImpl.class);
        this.simpleModule.addAbstractTypeMapping(Configuration.FilesConfiguration.class, FilesConfigurationImpl.class);

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
