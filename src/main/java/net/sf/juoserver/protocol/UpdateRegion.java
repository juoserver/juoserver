package net.sf.juoserver.protocol;

import java.util.Objects;

public class UpdateRegion extends AbstractMessage {

    protected static final int CODE = 0x57;
    private int unknown;

    public UpdateRegion() {
        super(CODE, 110);
    }

    public UpdateRegion(byte[] content) {
        this();
        var buffer = wrapContents(content);
        byte[] data = new byte[109];
        buffer.get(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRegion that = (UpdateRegion) o;
        return unknown == that.unknown;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unknown);
    }
}
