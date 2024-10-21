package net.sf.juoserver.protocol;

import java.util.Objects;

public class UpdateObjectChunk extends AbstractMessage {

    private static final int CODE = 0x3F;
    private int code;

    public UpdateObjectChunk() {
        super(CODE, 4);
        this.code = CODE;
    }

    public UpdateObjectChunk(byte[] contents) {
        this();
        int len = MessagesUtils.getLengthFromSecondAndThirdByte(contents);
        var buffer = wrapContents(3, contents);
        byte[] data = new byte[len];
        buffer.get(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateObjectChunk that = (UpdateObjectChunk) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
