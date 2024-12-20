package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Mobile;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * Select the npc informed via id. 0 (zero) to remove the selection
 */
public class AttackSucceed extends AbstractMessage {
	private static final long serialVersionUID = 1L;

	public static final int CODE = 0xAA;
	private static final int LENGTH = 5;

	private final int mobileID;
	
	public AttackSucceed(int mobileID) {	
		super(CODE, LENGTH);
		this.mobileID = mobileID;
	}
	
	public AttackSucceed(Mobile mobile) {	
		this(mobile.getSerialId());
	}
	
	@Override
	public ByteBuffer encode() {
		ByteBuffer bb = super.encode();
		bb.putInt(mobileID);
		return bb;

	}

	@Override
	public int hashCode() {
		return Objects.hash(mobileID);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AttackSucceed) {
			return Objects.equals(mobileID, ((AttackSucceed) obj).mobileID);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("Attack Succeed mobileId = %s", mobileID);
	}
}
