package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Item;

import java.nio.ByteBuffer;
import java.util.Objects;

public class ObjectInfo extends AbstractMessage {
	private static final int CODE = 0x1A;
	private static final long serialVersionUID = 1L;
	private final Item item;

	public ObjectInfo(Item item) {
		super(CODE, getLength(Objects.requireNonNull(item)));
		this.item = item;
	}

	private static int getLength(Item item) {
		return 1 + 2 + 4 + 2 + 2 + 2 + 2 + 1 + 2 + 1;
	}

	@Override
	public ByteBuffer encode() {
		ByteBuffer bb = super.encode();
		bb.putShort((short) getLength());
		bb.putInt(item.getSerialId() | 0x80000000); // To make the amount be read
		bb.putShort((short) (item.getModelId() & 0x3FFF));
		bb.putShort((short) item.getAmount()); // Amount info or Graphic id for death
		bb.putShort((short) (item.getX() & 0x7FFF)); // Never sending direction info
		bb.putShort((short) (item.getY() | 0x8000)); // Always sending hue info
		bb.put((byte) item.getZ());
		bb.putShort((short) item.getHue());
		bb.put((byte) 0x20); // Movable flag (TODO: item flags enum and move to Item)
		return bb;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ObjectInfo that = (ObjectInfo) o;
		return item.equals(that.item);
	}

	@Override
	public int hashCode() {
		return Objects.hash(item);
	}

	@Override
	public String toString() {
		return "ObjectInfo [item=" + item + ", x=" + item.getX() + ", y=" + item.getY() + ", z="
				+ item.getZ() + "]";
	}
}
