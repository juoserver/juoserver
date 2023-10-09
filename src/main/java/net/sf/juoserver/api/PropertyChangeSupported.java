package net.sf.juoserver.api;

import java.beans.PropertyChangeListener;

public interface PropertyChangeSupported {

    void addPropertyChangeListener(PropertyChangeListener listener);

    void addPropertyChangeListener(String property, PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(String property, PropertyChangeListener listener);

}
