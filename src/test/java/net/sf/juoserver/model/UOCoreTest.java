package net.sf.juoserver.model;

import net.sf.juoserver.api.ConfigFileReader;
import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.api.DataManager;
import net.sf.juoserver.api.FileReadersFactory;
import net.sf.juoserver.model.core.UOCore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UOCoreTest {

    private UOCore core;
    @Mock
    private FileReadersFactory fileReadersFactory;
    @Mock
    private Configuration.FilesConfiguration filesConfiguration;
    @Mock
    private DataManager dataManager;
    @Mock
    private Configuration configuration;
    @Mock
    private ConfigFileReader configFileReader;

    @BeforeEach
    public void setUp() {
        when(dataManager.getItemSerial()).thenReturn(1);
        when(configuration.getFiles()).thenReturn(filesConfiguration);
        when(filesConfiguration.getMulPath()).thenReturn(System.getProperty("user.dir"));
        core = new UOCore(fileReadersFactory, dataManager, configuration, configFileReader);
        core.init();
    }

}