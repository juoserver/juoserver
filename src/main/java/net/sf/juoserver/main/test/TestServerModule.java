package net.sf.juoserver.main.test;

import net.sf.juoserver.api.*;
import net.sf.juoserver.builder.JUOServerModule;
import net.sf.juoserver.protocol.AbstractCommand;
import net.sf.juoserver.protocol.CharacterAnimation;

import java.io.IOException;

public class TestServerModule extends JUOServerModule {
    public TestServerModule() {
        registerCommand(new AbstractCommand("test") {
            @Override
            public void execute(CommandContext context) {
                try {
                    context.getProtocolIoPort().sendToClient(new CharacterAnimation(context.getSession().getMobile(), AnimationRepeat.TWICE, AnimationType.GET_HIT, 10, AnimationDirection.BACKWARD));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
