package net.sf.juoserver.protocol;

import net.sf.juoserver.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandManagerImplTest {

    @Mock
    private PlayerContext context;
    @Mock
    private Configuration configuration;
    @Captor
    private ArgumentCaptor<List<String>> captor;

    @DisplayName("No args command was found")
    @Test
    public void noArgsCommandFound() {
        var speech = givenSpeechRequest(".noArgCommand");
        var command = givenCommand("noArgCommand");

        givenHandler(command).execute(speech);

        verify(command).execute(captor.capture(), ArgumentMatchers.eq(context));
        assertTrue(captor.getValue().isEmpty());
    }

    @DisplayName("Command context parameters validation")
    @Test
    public void commandContextParamsValid() {
        var speech = givenSpeechRequest(".context");
        var command = givenCommand("context");

        givenHandler(command).execute(speech);

        var captor = ArgumentCaptor.forClass(PlayerContext.class);
        verify(command).execute(any(), captor.capture());
        assertEquals(context, captor.getValue());
    }


    @Test
    public void multipleArgsCommand() {
        var speech = givenSpeechRequest(".multipleArgsCommand arg1 arg2");
        var command = givenCommand("multipleArgsCommand");

        givenHandler(command).execute(speech);

        verify(command).execute(captor.capture(), eq(context));

        assertIterableEquals(Arrays.asList("arg1", "arg2"), captor.getValue());
    }

    @Test
    public void findCommandIgnoringCase() {
        var speech = givenSpeechRequest(".COMMAND");
        var command = givenCommand("command");

        givenHandler(command).execute(speech);

        verify(command).execute(any(), any(PlayerContext.class));
    }

    private Command givenCommand(String name) {
        var command = mock(Command.class);
        when(command.getName()).thenReturn(name);
        return command;
    }

    private UnicodeSpeechRequest givenSpeechRequest(String text) {
        return new UnicodeSpeechRequest(MessageType.Mobile, 0, 0, "ENU", text);
    }

    private CommandManager givenHandler(Command command) {
        var commandConfig = mock(Configuration.CommandConfiguration.class);
        lenient().when(commandConfig.getActivationChar()).thenReturn(".");
        lenient().when(configuration.getCommand()).thenReturn(commandConfig);
        return new CommandManagerImpl(context, Collections.singleton(command), configuration);
    }
}