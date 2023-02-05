package net.sf.juoserver.main.test;

import net.sf.juoserver.api.CommandContext;
import net.sf.juoserver.builder.JUOServerModule;
import net.sf.juoserver.protocol.AbstractCommand;

import java.util.Collections;

public class TestServerModule extends JUOServerModule {

    public TestServerModule() {
        registerCommand(new AbstractCommand("command") {
            @Override
            public void execute(CommandContext context) {
                //try {
                var mobile = context.getSession().getMobile();
                var core = context.getCore();
                var session = context.getSession();

                    //var item = new UOItem(UOCore.ITEMS_MAX_SERIAL_ID+x++, 0x2006, 0);
                /*var item = core.createItem(0x2006)
                        .setAmount(0x190)
                        .setLocation(mobile.getX(), mobile.getY()-11, mobile.getZ())
                        .setName("Asder corpse");
                session.createGroundItem(item);*/

                for (int x=-1; x<1; x++) {
                    session.createGroundItems(Collections.singleton(core.createItem(0x0ED5)
                            .setLocation(mobile.getX()+x, mobile.getY()-11, mobile.getZ())
                            .setName("Test")));
                }

                    //context.getProtocolIoPort().sendToClient(new CharacterAnimation(context.getSession().getMobile(), AnimationRepeat.TWICE, AnimationType.GET_HIT, 10, AnimationDirection.BACKWARD));
                /*} catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
            }
        });
    }
}
