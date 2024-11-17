package net.sf.juoserver.model.npc;

import net.sf.juoserver.api.NpcContext;

import java.util.HashMap;
import java.util.Map;

public class UONpcContext implements NpcContext {
    private final Map<String, Object> contextProps = new HashMap<>();

    @Override
    public <T> void addEntry(String key, T value) {
        this.contextProps.put(key, value);
    }

    @Override
    public <T> T getEntry(String key, T defaultValue) {
        return (T) contextProps.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean containsEntryKey(String key) {
        return contextProps.containsKey(key);
    }
}
