package net.sf.juoserver.main.test;

import net.sf.juoserver.api.CommandContext;
import net.sf.juoserver.api.MessageType;
import net.sf.juoserver.protocol.AbstractCommand;
import net.sf.juoserver.protocol.UnicodeSpeech;

import java.io.IOException;

public class Where extends AbstractCommand {
    public Where() {
        super("where");
    }

    @Override
    public void execute(CommandContext context) {
        var mobile = context.getSession().getMobile();
        try {
            context.getProtocolIoPort().sendToClient(new UnicodeSpeech(mobile, MessageType.System, 0x481, 0, "en_US", String.format("X: %s - Y: %s - Z: %s", mobile.getX(), mobile.getY(), mobile.getZ())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
