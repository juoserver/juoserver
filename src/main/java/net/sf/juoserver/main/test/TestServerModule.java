package net.sf.juoserver.main.test;

import net.sf.juoserver.api.CommandContext;
import net.sf.juoserver.builder.JUOServerModule;
import net.sf.juoserver.protocol.AbstractCommand;

public class TestServerModule extends JUOServerModule {

    public TestServerModule() {
        registerCommand(new AbstractCommand("command") {
            @Override
            public void execute(CommandContext context) {
                //try {
                    var mobile = context.getSession().getMobile();

                    //var item = new UOItem(UOCore.ITEMS_MAX_SERIAL_ID+x++, 0x2006, 0);
                    var item = context.getCore().createItem(0x2006)
                            .setAmount(0x190)
                            .setLocation(mobile)
                            .setName("Asder corpse");

                context.getSession().createGroundItem(item);

                    //context.getProtocolIoPort().sendToClient(new CharacterAnimation(context.getSession().getMobile(), AnimationRepeat.TWICE, AnimationType.GET_HIT, 10, AnimationDirection.BACKWARD));
                /*} catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
            }
        });
    }
}
