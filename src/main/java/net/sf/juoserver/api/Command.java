package net.sf.juoserver.api;

import java.util.List;

public interface Command extends EventHandler<List<String>> {

    String getName();

}
