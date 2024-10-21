package net.sf.juoserver.protocol;

import java.util.Objects;

public class RemoveStaticObject extends AbstractMessage {

    protected static final int CODE = 0x61;
    private int x;
    private int y;
    private int z;
    private int staticObjectId;

    public RemoveStaticObject() {
        super(CODE, 9);
    }

    public RemoveStaticObject(byte[] content) {
        this();
        var buffer = wrapContents(content);
        this.x = buffer.getShort();
        this.y = buffer.getShort();
        this.z = buffer.getShort();
        this.staticObjectId = buffer.getShort();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getStaticObjectId() {
        return staticObjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoveStaticObject that = (RemoveStaticObject) o;
        return x == that.x && y == that.y && z == that.z && staticObjectId == that.staticObjectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, staticObjectId);
    }
}
