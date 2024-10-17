package net.sf.juoserver.main.test;

import net.sf.juoserver.api.*;
import net.sf.juoserver.builder.JUOServerModule;
import net.sf.juoserver.protocol.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TestServerModule extends JUOServerModule {

    public TestServerModule() {
        registerCommand(new Where());
        registerCommand(new Move());
        registerCommand(new AbstractCommand("death") {
            @Override
            public void execute(List<String> source, PlayerContext context) {
                //try {

                var mobile = context.session().getMobile();
                var core = context.core();
                var session = context.session();

                //var item = new UOItem(UOCore.ITEMS_MAX_SERIAL_ID+x++, 0x2006, 0);
                /*var item = core.createItem(0x2006)
                        .amount(0x190)
                        .location(mobile.getX(), mobile.getY(), mobile.getZ())
                        .name("Asder corpse");*/
                try {
                   // session.showGroundItems(List.of(item));
                    //context.protocolIoPort().sendToClient(new CharacterAnimation(mobile, AnimationRepeat.ONCE, AnimationType.SALUTE, 300, AnimationDirection.FORWARD));

                    mobile.kill();
                    context.protocolIoPort().sendToClient(new DeathScreen(DeathAction.SERVER_SENT), new CharacterDraw(mobile), new StatusBarInfo(mobile));
                    //context.protocolIoPort().sendToClient(new DeathAnimation(mobile, 8198));
                    //context.protocolIoPort().sendToClient(new UnicodeSpeech(mobile, MessageType.System, 0x481, 0, "en_US", "opa neguinho"));

                } catch (Exception exception) {
                    exception.printStackTrace();
                }


                /*for (int x=-1; x<1; x++) {
                    session.showGroundItems(Collections.singleton(core.createItem(0x0ED5)
                            .location(mobile.getX()+x, mobile.getY()-11, mobile.getZ())
                            .name("Test")));
                }*/
            }

        });

        registerCommand(new AbstractCommand("res") {
            @Override
            public void execute(List<String> source, PlayerContext context) {
                var mobile = context.session().getMobile();
                mobile.revive();
                try {
                    context.protocolIoPort().sendToClient(new DeathScreen(DeathAction.RESURRECT), new CharacterDraw(mobile), new StatusBarInfo(mobile));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
