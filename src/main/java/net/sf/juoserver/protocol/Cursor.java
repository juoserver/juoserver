package net.sf.juoserver.protocol;

import net.sf.juoserver.api.CursorTarget;
import net.sf.juoserver.api.CursorType;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class Cursor extends AbstractMessage {

    public static final int CODE = 0x6C;
    private CursorTarget target;
    private int cursorId;
    private CursorType type;
    private int serialId;
    private int x;
    private int y;
    private int z;
    private int modelId;

    public Cursor() {
        super(CODE, 19);
    }

    public Cursor(byte[] content) {
        this();
        var buffer = wrapContents(content);
        this.target = CursorTarget.byCode(buffer.get());
        this.cursorId = buffer.getInt();
        this.type = CursorType.byCode(buffer.get());
        this.serialId = buffer.getInt();
        this.x = buffer.getShort();
        this.y = buffer.getShort();
        buffer.get(); // unknown
        this.z = buffer.get();
        this.modelId = buffer.getShort();
    }

    public Cursor(CursorTarget target, int cursorId, CursorType type) {
        this();
        this.target = target;
        this.cursorId = cursorId;
        this.type = type;
    }

    public CursorTarget getTarget() {
        return target;
    }

    public int getCursorId() {
        return cursorId;
    }

    public CursorType getType() {
        return type;
    }

    public int getSerialId() {
        return serialId;
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

    public int getModelId() {
        return modelId;
    }

    @Override
    public ByteBuffer encode() {
        var buffer = super.encode();
        buffer.put((byte) target.getCode());
        buffer.putInt(cursorId);
        buffer.put((byte) type.getCode());
        var noData = new byte[12];
        Arrays.fill(noData, (byte) 0 );
        buffer.put(noData);
        return buffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursor cursor = (Cursor) o;
        return cursorId == cursor.cursorId && serialId == cursor.serialId && x == cursor.x && y == cursor.y && z == cursor.z && modelId == cursor.modelId && target == cursor.target && type == cursor.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, cursorId, type, serialId, x, y, z, modelId);
    }
}
