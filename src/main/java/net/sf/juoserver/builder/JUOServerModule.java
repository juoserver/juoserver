package net.sf.juoserver.builder;

import net.sf.juoserver.api.Command;
import net.sf.juoserver.api.ServerModule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JUOServerModule implements ServerModule {

    private final Set<Command> commands = new HashSet<>();

    public JUOServerModule registerCommand(Command command) {
        this.commands.add(command);
        return this;
    }

    @Override
    public Collection<Command> getCommands() {
        return commands;
    }
}
