package net.sf.juoserver.protocol;

import net.sf.juoserver.api.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class CommandHandlerImplTest {

    @Mock
    private Core core;
    @Mock
    private InterClientNetwork network;
    @Mock
    private Configuration configuration;
    @Mock
    private PlayerSession session;
    @Mock
    private ProtocolIoPort protocolIoPort;

    @DisplayName("No args command was found")
    @Test
    public void noArgsCommandFound() {
        var speech = givenSpeechRequest(".noArgCommand");
        var command = givenCommand("noArgCommand");

        givenHandler(command).execute(protocolIoPort, session, speech);

        var captor = ArgumentCaptor.forClass(CommandContext.class);
        verify(command).execute(captor.capture());
        assertTrue(captor.getValue().getArguments().isEmpty());
    }

    @DisplayName("Command context parameters validation")
    @Test
    public void commandContextParamsValid() {
        var speech = givenSpeechRequest(".context");
        var command = givenCommand("context");

        givenHandler(command).execute(protocolIoPort, session, speech);

        var captor = ArgumentCaptor.forClass(CommandContext.class);
        verify(command).execute(captor.capture());
        assertEquals(core, captor.getValue().getCore());
        assertEquals(network, captor.getValue().getNetwork());
        assertEquals(session, captor.getValue().getSession());
    }

    @Test
    public void multipleArgsCommand() {
        var speech = givenSpeechRequest(".multipleArgsCommand arg1 arg2");
        var command = givenCommand("multipleArgsCommand");

        givenHandler(command).execute(protocolIoPort, session, speech);

        var captor = ArgumentCaptor.forClass(CommandContext.class);
        verify(command).execute(captor.capture());

        assertIterableEquals(Arrays.asList("arg1", "arg2"), captor.getValue().getArguments());
    }

    @Test
    public void findCommandIgnoringCase() {
        var speech = givenSpeechRequest(".COMMAND");
        var command = givenCommand("command");

        givenHandler(command).execute(protocolIoPort, session, speech);

        verify(command).execute(ArgumentMatchers.any(CommandContext.class));
    }

    private Command givenCommand(String name) {
        var command = mock(Command.class);
        when(command.getName()).thenReturn(name);
        return command;
    }

    private UnicodeSpeechRequest givenSpeechRequest(String text) {
        return new UnicodeSpeechRequest(MessageType.Mobile, 0, 0, "ENU", text);
    }

    private CommandHandler givenHandler(Command command) {
        return new CommandHandlerImpl(core, network, Collections.singleton(command), configuration);
    }
}