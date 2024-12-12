package net.sf.juoserver.main.test;

import net.sf.juoserver.api.CursorTarget;
import net.sf.juoserver.api.CursorType;
import net.sf.juoserver.api.PlayerContext;
import net.sf.juoserver.protocol.AbstractCommand;

import java.util.List;

public class KIll extends AbstractCommand {
    public KIll() {
        super("mate");
    }

    @Override
    public void execute(List<String> source, PlayerContext context) {
        var session = context.session();
        session.sendCursor(CursorType.HARMFUL, CursorTarget.SELECT_OBJECT)
                .whenComplete(((cursor, throwable) -> {
                    var npc = context.core().findMobileByID(cursor.getSerialId());
                    context.session().applyDamageTo(npc, 200);
                    //context.protocolIoPort().sendToClient(new DeleteItem(cursor.getSerialId()));
                }));
    }
}
