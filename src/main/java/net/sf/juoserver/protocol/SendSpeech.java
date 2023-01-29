package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.Mobile;
import net.sf.juoserver.api.TextType;

import java.nio.ByteBuffer;

public class SendSpeech extends AbstractMessage {
    protected static final int CODE = 0x1c;

    private int serialId;
    private int modelId;
    private TextType textType;
    private int textColor;
    private int textFont;
    private String name;
    private String message;

    public SendSpeech(Item item) {
        this(item.getSerialId(), item.getModelId(), TextType.NORMAL, 0, 0, "", item.getName());
    }

    public SendSpeech(Mobile mobile) {
        this(mobile.getSerialId(), mobile.getModelId(), TextType.NORMAL, 0, 0,  "", mobile.getName());
    }

    public SendSpeech(int serialId, int modelId, TextType textType, int textColor, int textFont, String name, String message) {
        super(CODE, 44 + message.length());
        this.serialId = serialId;
        this.modelId = modelId;
        this.textType = textType;
        this.textColor = textColor;
        this.textFont = textFont;
        this.name = name;
        this.message = message;
    }


    @Override
    public ByteBuffer encode() {
        var buffer = super.encode();
        buffer.putShort((short) getLength());
        buffer.putInt(serialId);
        buffer.putShort((short) modelId);
        buffer.put((byte) textType.getCode());
        buffer.putShort((short) textColor);
        buffer.putShort((short) textFont);
        buffer.put(MessagesUtils.padString(name, 30));
        buffer.put(message.getBytes());
        return buffer;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
