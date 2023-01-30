package net.sf.juoserver.configuration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.org.webcompere.systemstubs.SystemStubs.*;


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

        assertEquals(9999, configuration.getStats().getMaxHitPoints());
    }

    @Test
    public void shouldUseExternalYamlInfo() throws IOException {
        Files.writeString(externalYaml, "stats:\n  maxHitPoints: 9090");

        var configuration = ConfigurationFactory.newInstance().newConfiguration();

        assertEquals(9090, configuration.getStats().getMaxHitPoints());
    }

    @Test
    public void shouldUseExternalProperty() throws IOException {
        Files.writeString(externalProperty, "stats.maxHitPoints=8080");

        var configuration = ConfigurationFactory.newInstance().newConfiguration();

        assertEquals(8080, configuration.getStats().getMaxHitPoints());
    }

    @Test
    public void shouldUseEnvVars() throws Exception {
        withEnvironmentVariable("stats.maxHitPoints", "7070")
                .execute(()->{
                    var configuration = ConfigurationFactory.newInstance().newConfiguration();
                    assertEquals(7070, configuration.getStats().getMaxHitPoints());
                });
    }
}