package net.sf.juoserver.main.test;

import net.sf.juoserver.api.CursorTarget;
import net.sf.juoserver.api.CursorType;
import net.sf.juoserver.api.PlayerContext;
import net.sf.juoserver.protocol.AbstractCommand;
import net.sf.juoserver.protocol.CharacterDraw;
import net.sf.juoserver.protocol.ObjectRevision;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CreateNPC extends AbstractCommand {
    public CreateNPC() {
        super("npc");
    }

    @Override
    public void execute(List<String> args, PlayerContext context) {
        //context.core().createItem()



            /*var cursor = context.session().sendCursor(CursorType.NEUTRAL, CursorTarget.SELECT_OBJECT)
                    .whenComplete((cursor1, throwable) -> {
                        System.out.println(cursor1.getSerialId());
                    });*/

            //context.protocolIoPort().sendToClient(new Cursor(CursorTarget.SELECT_LOCATION, 1, CursorType.NEUTRAL));

            if (!args.isEmpty()) {
                var id = Integer.parseInt(args.get(0).substring(2));
                var mobile = context.session().getMobile();
                var npc = context.core().createNpcAtLocation(id, context.session().getMobile());
            }

    }
}
