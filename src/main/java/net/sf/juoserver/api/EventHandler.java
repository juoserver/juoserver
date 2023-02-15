package net.sf.juoserver.api;

public interface EventHandler<T> {
    void execute(T source, PlayerContext context);
}
