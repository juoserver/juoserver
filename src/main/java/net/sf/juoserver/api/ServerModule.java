package net.sf.juoserver.api;

import net.sf.juoserver.api.Command;

import java.util.Collection;

public interface ServerModule {

    Collection<Command> getCommands();

}
