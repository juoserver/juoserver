package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Container;

import java.nio.ByteBuffer;

public class DrawContainer extends AbstractMessage {
	private static final long serialVersionUID = 1L;
	public static final int CODE = 0x24;
	private int containerSerialId;
	private int containerGumpId;

	public DrawContainer(Container cont) {
		super(CODE, 9);
		this.containerSerialId = cont.getSerialId();
		this.containerGumpId = cont.getGumpId();
	}

	@Override
	public ByteBuffer encode() {
		ByteBuffer bb = super.encode();
		bb.putInt(containerSerialId);
		bb.putShort((short) containerGumpId);
		bb.putShort((short)0x7D);
		return bb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + containerGumpId;
		result = prime * result + containerSerialId;
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
		DrawContainer other = (DrawContainer) obj;
		if (containerGumpId != other.containerGumpId)
			return false;
		if (containerSerialId != other.containerSerialId)
			return false;
		return true;
	}
}
