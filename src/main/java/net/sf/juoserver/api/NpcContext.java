package net.sf.juoserver.api;

public interface NpcContext {

    <T> void addEntry(String key, T value);

    <T> T getEntry(String key, T defaultValue);

    boolean containsEntryKey(String key);
}
