package net.sf.juoserver.protocol;

import java.util.Objects;

public class NewTerrain extends AbstractMessage {

    protected static final int CODE = 0x47;

    private int x;
    private int y;
    private int artId;
    private int width;
    private int height;

    public NewTerrain() {
        super(CODE, 11);
    }

    public NewTerrain(byte[] content) {
        this();
        var buffer = wrapContents(content);
        this.x = buffer.getShort();
        this.y = buffer.getShort();
        this.artId = buffer.getShort();
        this.width = buffer.getShort();
        this.height = buffer.getShort();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getArtId() {
        return artId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTerrain that = (NewTerrain) o;
        return x == that.x && y == that.y && artId == that.artId && width == that.width && height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, artId, width, height);
    }
}
