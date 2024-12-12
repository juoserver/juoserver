package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Mobile;

import java.nio.ByteBuffer;

public class DeleteObject extends AbstractMessage {
	private static final long serialVersionUID = 1L;
	private static final int CODE = 0x1D;
	private int itemSerialId;

	public DeleteObject(int itemSerialId) {
		this();
		this.itemSerialId = itemSerialId;
	}

	public DeleteObject(Mobile mobile) {
		this();
		this.itemSerialId = mobile.getSerialId();
	}

	public DeleteObject() {
		super(CODE, 5);
	}

	@Override
	public ByteBuffer encode() {
		ByteBuffer bb = super.encode();
		bb.putInt(itemSerialId);
		return bb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemSerialId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeleteObject other = (DeleteObject) obj;
		if (itemSerialId != other.itemSerialId)
			return false;
		return true;
	}
}
