package net.sf.juoserver.protocol;

import java.nio.ByteBuffer;

public class SendSpeech extends AbstractMessage {
    protected static final int CODE = 0x1c;

    private int serialId;
    private int modelId;
    private int textType;
    private int textColor;
    private int textFont;
    private String name;
    private String message;

    public SendSpeech(int serialId, int modelId, int textType, int textColor, int textFont, String name, String message) {
        super(CODE, 44);
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
        buffer.put((byte) 0x00);
        buffer.putShort((short) textColor);
        buffer.putShort((short) textFont);
        buffer.put(MessagesUtils.padString(name, 30));
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
