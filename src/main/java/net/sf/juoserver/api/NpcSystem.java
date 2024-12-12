package net.sf.juoserver.api;

public interface NpcSystem {

    /**
     * Register listener for newly session created
     * @param listener Listener
     */
    void addNpcSessionListener(NpcSessionListener listener);

    /**
     * Register listener for session removal
     * @param listener
     */
    void removeNpcSessionListener(NpcSessionListener listener);
}
