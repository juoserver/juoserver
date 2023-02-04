package net.sf.juoserver.main.test;

import net.sf.juoserver.api.*;
import net.sf.juoserver.builder.JUOServerModule;
import net.sf.juoserver.protocol.*;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class TestServerModule extends JUOServerModule {
    public TestServerModule() {
        registerCommand(new AbstractCommand("command") {
            @Override
            public void execute(CommandContext context) {
                try {
                    var mobile = context.getSession().getMobile();
                    //context.getProtocolIoPort().sendToClient(new DeathAction(context.getSession().getMobile(), 0x3CA),new Death(2), new UnicodeSpeech(mobile, MessageType.Regular, 0,0,"en_US", "Message Sent"));

                    context.getProtocolIoPort().sendToClient(new CharacterDraw(mobile.getSerialId(), 0x3CA, mobile.getX(), mobile.getY(), mobile.getZ(), (byte)mobile.getDirection().getCode(),mobile.getHue(), mobile.getCharacterStatus(), mobile.getNotoriety(), Collections.emptyMap()));
                    context.getProtocolIoPort().sendToClient(new ObjectInfo(new CorpseItem(), mobile.getX(), mobile.getY(), mobile.getZ()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
