package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Mobile;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Damage extends AbstractMessage {
	
	private static final long serialVersionUID = 1L;
	private static final int CODE = 0x0B;

	private int damageDealt;
	private int serialId;
	
	public Damage() {
		super(CODE, 7);		
	}	

	public Damage(int serialId, int damage) {
		this();
		this.serialId = serialId;
		this.damageDealt = damage;
	}

	public Damage(Mobile mobile, int damage) {
		this(mobile.getSerialId(), damage);
	}
	
	@Override
	public ByteBuffer encode() {
		ByteBuffer buffer = super.encode();
		buffer.putInt(serialId);
		buffer.putShort((short)damageDealt); // Max value 0xFFFF
		return buffer;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(serialId, damageDealt);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Damage) {
			return Objects.equals(serialId, ((Damage) obj).serialId) &&
					Objects.equals(damageDealt, ((Damage) obj).damageDealt);
		}
		return false;
	}

}
