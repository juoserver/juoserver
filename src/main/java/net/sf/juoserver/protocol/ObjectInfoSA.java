package net.sf.juoserver.protocol;

import java.util.Objects;

public class ObjectInfoSA extends AbstractMessage {

    private static final int CODE = 0xF3;
    private int dataType;
    private int serial;
    private int graphic; // for multi its same value as the multi has in multi.mul
    private int facing; // 0x00 if Multi
    private int amount;  // 0x1 if Multi , no idea why Amount is sent 2 times
    private int x;
    private int y;
    private int z;
    private int layer; // 0x00 if Multi
    private int color;  // 0x00 if Multi
    private int flag;  // 0x20 = Movable if normally not , 0x80 = Hidden , 0x00 if Multi

    public ObjectInfoSA() {
        super(CODE, 24);
    }

    public ObjectInfoSA(byte[] content) {
        this();
        var buffer = wrapContents(content);
        buffer.getShort(); // 0x1 // always 0x1 on OSI
        this.dataType = buffer.get();
        this.serial = buffer.getInt();
        this.graphic = buffer.getShort();
        this.facing = buffer.get();
        this.amount = buffer.getShort();
        buffer.getShort();  // no idea why Amount is sent 2 times
        this.x = buffer.getShort();
        this.y = buffer.getShort();
        this.z = buffer.get();
        this.layer = buffer.get();
        this.color = buffer.getShort();
        this.flag = buffer.get();
    }

    public int getDataType() {
        return dataType;
    }

    public int getSerial() {
        return serial;
    }

    public int getGraphic() {
        return graphic;
    }

    public int getFacing() {
        return facing;
    }

    public int getAmount() {
        return amount;
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

    public int getLayer() {
        return layer;
    }

    public int getColor() {
        return color;
    }

    public int getFlag() {
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectInfoSA that = (ObjectInfoSA) o;
        return serial == that.serial;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serial);
    }
}
