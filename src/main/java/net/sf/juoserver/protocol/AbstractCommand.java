package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Command;

public abstract class AbstractCommand implements Command {
    private final String name;

    public AbstractCommand(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
