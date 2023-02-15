package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.Mobile;

import java.nio.ByteBuffer;
import java.util.Objects;

public class DragItem extends AbstractMessage {
	private static final long serialVersionUID = 1L;

	private static final int CODE = 0x23;

	private Item item;
	private int itemAmount;
	private Mobile source;
	private int targetSerial;
	
	public DragItem(Item item, Mobile source, int targetSerial) {
		super(CODE, 26);
		this.item = item;
		this.itemAmount = itemAmount;
		this.source = source;
		this.targetSerial = targetSerial;
	}

	@Override
	public ByteBuffer encode() {
		ByteBuffer bb = super.encode();
		bb.putShort((short) item.getModelId());
		bb.put((byte) 0);
		bb.putShort((short) item.getHue()); // TODO: probably useless
		bb.putShort((short) item.amount());
		bb.putInt(source.getSerialId());
		bb.putShort((short) source.getX());
		bb.putShort((short) source.getY());
		bb.put((byte) source.getZ());
		bb.putInt(targetSerial); // Parent, zero represent no parent - TODO: this is going to have to be changeable
		bb.putShort((short) item.getX());
		bb.putShort((short) item.getY());
		bb.put((byte) item.getZ());
		return bb;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DragItem dragItem = (DragItem) o;
		return itemAmount == dragItem.itemAmount && targetSerial == dragItem.targetSerial && item.equals(dragItem.item) && Objects.equals(source, dragItem.source);
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, itemAmount, source, targetSerial);
	}

	@Override
	public String toString() {
		return "DragItem [item=" + item + ", itemAmount=" + itemAmount
				+ ", source=" + source + ", targetSerial=" + targetSerial;
	}
}
