package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Command;
import net.sf.juoserver.api.CommandManager;
import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.api.PlayerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CommandManagerImpl implements CommandManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandManagerImpl.class);
    private final Map<String, Command> commands;
    private final String activationCharacter;
    private PlayerContext context;

    public CommandManagerImpl(Collection<Command> commands, Configuration configuration) {
        this.commands = commands.stream()
                .collect(Collectors.toMap(command->command.getName().toLowerCase(), e->e));
        this.activationCharacter = configuration.getCommand().getActivationChar();
    }

    @Override
    public void setContext(PlayerContext context) {
        this.context = context;
    }

    @Override
    public boolean isCommand(UnicodeSpeechRequest request) {
        return request.getText().startsWith(activationCharacter);
    }

    @Override
    public void execute(UnicodeSpeechRequest request) {
        StringTokenizer tokenizer = new StringTokenizer(request.getText().substring(1), " ");
        String commandName = tokenizer.nextToken().toLowerCase();
        if (commands.containsKey(commandName)) {
            commands.get(commandName).execute(getCommandArgs(tokenizer), context);
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
