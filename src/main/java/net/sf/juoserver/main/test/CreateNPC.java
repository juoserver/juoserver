package net.sf.juoserver.main.test;

import net.sf.juoserver.api.*;
import net.sf.juoserver.protocol.AbstractCommand;
import net.sf.juoserver.protocol.CharacterDraw;
import net.sf.juoserver.protocol.Cursor;
import net.sf.juoserver.protocol.ObjectRevision;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

public class CreateNPC extends AbstractCommand {
    public CreateNPC() {
        super("npc");
    }

    @Override
    public void execute(List<String> source, PlayerContext context) {
        //context.core().createItem()


        try {
            var cursor = context.session().sendCursor(CursorType.NEUTRAL, CursorTarget.SELECT_OBJECT)
                    .whenComplete((cursor1, throwable) -> {
                        System.out.println(cursor1.getSerialId());
                    });

            //context.protocolIoPort().sendToClient(new Cursor(CursorTarget.SELECT_LOCATION, 1, CursorType.NEUTRAL));

            var mobile = context.session().getMobile();
            var npc = context.core().createNpc(context.session().getMobile());
            context.protocolIoPort().sendToClient(new CharacterDraw(npc), new ObjectRevision(mobile));
            npc.moveForward();
            context.protocolIoPort().sendToClient(new CharacterDraw(npc), new ObjectRevision(mobile));
            npc.moveForward();
            context.protocolIoPort().sendToClient(new CharacterDraw(npc), new ObjectRevision(mobile));
            npc.moveForward();
            context.protocolIoPort().sendToClient(new CharacterDraw(npc), new ObjectRevision(mobile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
