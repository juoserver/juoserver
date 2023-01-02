package net.sf.juoserver.api;

public interface Command {

    String getName();
    void execute(CommandContext context);

}
