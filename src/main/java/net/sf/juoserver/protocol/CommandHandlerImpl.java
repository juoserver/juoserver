package net.sf.juoserver.protocol;

import net.sf.juoserver.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CommandHandlerImpl implements CommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandHandlerImpl.class);
    private final Core core;
    private final InterClientNetwork network;
    private final Map<String, Command> commands;
    private final String activationCharacter;

    public CommandHandlerImpl(Core core, InterClientNetwork network, Collection<Command> commands, Configuration configuration) {
        this.core = core;
        this.network = network;
        this.commands = commands.stream()
                .collect(Collectors.toMap(command->command.getName().toLowerCase(), e->e));
        this.activationCharacter = configuration.getCommand().getActivationChar();
    }

    @Override
    public boolean isCommand(UnicodeSpeechRequest request) {
        return request.getText().startsWith(activationCharacter);
    }

    @Override
    public void execute(ProtocolIoPort protocolIoPort, PlayerSession session, UnicodeSpeechRequest request) {
        StringTokenizer tokenizer = new StringTokenizer(request.getText().substring(1), " ");
        String commandName = tokenizer.nextToken().toLowerCase();
        if (commands.containsKey(commandName)) {
            commands.get(commandName).execute(new CommandContext(core, network, session, protocolIoPort, getCommandArgs(tokenizer)));
        } else {
            LOGGER.info("Unknown Command {}", commandName);
        }
    }

    private List<String> getCommandArgs(StringTokenizer tokenizer) {
        List<String> args = new LinkedList<>();
        while (tokenizer.hasMoreElements()) {
            args.add(tokenizer.nextToken());
        }
        return args;
    }
}
