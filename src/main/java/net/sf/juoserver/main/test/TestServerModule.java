package net.sf.juoserver.main.test;

import net.sf.juoserver.api.Command;
import net.sf.juoserver.api.CommandContext;
import net.sf.juoserver.builder.JUOServerModule;
import net.sf.juoserver.protocol.AbstractCommand;

public class TestServerModule extends JUOServerModule {
    public TestServerModule() {
        registerCommand(new AbstractCommand("teste") {
            @Override
            public void execute(CommandContext context) {
                System.out.println("foi");
            }
        });
    }
}
