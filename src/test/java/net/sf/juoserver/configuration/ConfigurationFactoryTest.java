package net.sf.juoserver.configuration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ConfigurationFactoryTest {

    private Path externalYaml;
    private Path externalProperty;

    @BeforeEach
    public void setUp() {
        externalYaml = Path.of("configuration.yaml");
        externalProperty = Path.of("configuration.properties");
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (Files.exists(externalYaml)) {
            Files.delete(externalYaml);
        }
        if (Files.exists(externalProperty)) {
            Files.delete(externalProperty);
        }
    }

    @Test
    public void shouldUseClasspathInfo() {
        var configuration = ConfigurationFactory.newInstance().newConfiguration();

        assertEquals(9999, configuration.getStats().getLifeLimit());
    }

    @Test
    public void shouldUseExternalYamlInfo() throws IOException {
        Files.write(externalYaml, "stats:\n  lifeLimit: 9090".getBytes(StandardCharsets.UTF_8));

        var configuration = ConfigurationFactory.newInstance().newConfiguration();

        assertEquals(9090, configuration.getStats().getLifeLimit());
    }

    @Test
    public void shouldUseExternalProperty() throws IOException {
        Files.write(externalProperty, "stats.lifeLimit=8080".getBytes(StandardCharsets.UTF_8));

        var configuration = ConfigurationFactory.newInstance().newConfiguration();

        assertEquals(8080, configuration.getStats().getLifeLimit());
    }

    @Test
    @SetEnvironmentVariable(key = "stats.lifeLimit", value = "7070")
    public void shouldUseEnvVars() {
        var configuration = ConfigurationFactory.newInstance().newConfiguration();

        assertEquals(7070, configuration.getStats().getLifeLimit());
    }
}