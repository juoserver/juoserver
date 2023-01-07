package net.sf.juoserver.main;

import net.sf.juoserver.api.JUOServer;
import net.sf.juoserver.builder.JUOServerBuilder;
import net.sf.juoserver.builder.ServerType;
import net.sf.juoserver.main.test.TestServerModule;

import java.io.IOException;

public class ApplicationMain {
    public static void main(String[] args) throws IOException {
        JUOServer server = JUOServerBuilder.newInstance()
                .registerModule(new TestServerModule())
                .build();
        server.run();
    }
}
