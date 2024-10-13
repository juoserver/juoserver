package net.sf.juoserver.protocol;

import java.util.Objects;

public class ChangePassword extends AbstractMessage {

    protected static final int CODE = 0x84;
    private int code;

    public ChangePassword() {
        super(CODE, 4);
    }

    public ChangePassword(byte[] content) {
        this();
        var buffer = wrapContents(content);
        buffer.get();
        this.code = buffer.getShort();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePassword that = (ChangePassword) o;
        return Objects.equals(code, that.code);
    }
}
