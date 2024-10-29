package net.sf.juoserver.main.test;

import net.sf.juoserver.api.CharacterStatus;
import net.sf.juoserver.api.Direction;
import net.sf.juoserver.api.Notoriety;
import net.sf.juoserver.api.PlayerContext;
import net.sf.juoserver.protocol.AbstractCommand;
import net.sf.juoserver.protocol.CharacterDraw;
import net.sf.juoserver.protocol.ObjectRevision;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CreateNPC extends AbstractCommand {
    public CreateNPC() {
        super("npc");
    }

    @Override
    public void execute(List<String> source, PlayerContext context) {
        //context.core().createItem()
        try {
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
