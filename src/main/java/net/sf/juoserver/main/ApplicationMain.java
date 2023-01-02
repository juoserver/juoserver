package net.sf.juoserver.main;

import net.sf.juoserver.api.JUOServer;
import net.sf.juoserver.builder.JUOServerBuilder;

import java.io.IOException;

public class ApplicationMain {
    public static void main(String[] args) throws IOException {
        JUOServer server = JUOServerBuilder.newInstance()
                .build();
        server.run();
    }
}
