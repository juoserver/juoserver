package net.sf.juoserver.protocol;

import java.util.Objects;

public class BuffDebuff extends AbstractMessage {
    public static final int CODE = 0x05;

    private Integer length;
    private Integer serial;
    private Integer attributedId;

    public BuffDebuff() {
        super(CODE, 9);
    }

    public BuffDebuff(byte[] content) {
        this();
        var buffer = wrapContents(content);
        this.length = (int) buffer.getShort();
        this.serial = buffer.getInt();
        this.attributedId = buffer.getInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuffDebuff that = (BuffDebuff) o;
        return Objects.equals(serial, that.serial) && Objects.equals(attributedId, that.attributedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serial, attributedId);
    }
}
