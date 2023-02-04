package net.sf.juoserver.protocol;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Death extends AbstractMessage {

    protected static final int CODE = 0x2c02;
    private int action;

    public Death(int action) {
        super(CODE, 2);
        this.action = action;
    }

    @Override
    public ByteBuffer encode() {
        var buffer  = super.encode();
        buffer.put((byte) action);
        return buffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Death death = (Death) o;
        return action == death.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action);
    }
}
