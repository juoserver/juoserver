package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Mobile;

import java.nio.ByteBuffer;
import java.util.Objects;

public class DeathAction extends AbstractMessage {
    protected static final int CODE = 0xAF;

    private final Mobile mobile;
    private final int corpseId;

    public DeathAction(Mobile mobile, int corpseId) {
        super(CODE, 13);
        this.mobile = mobile;
        this.corpseId = corpseId;
    }

    @Override
    public ByteBuffer encode() {
        var buffer = super.encode();
        buffer.putInt(mobile.getSerialId());
        buffer.putInt(corpseId);
        buffer.putInt(0);
        return buffer;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public int getCorpseId() {
        return corpseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeathAction that = (DeathAction) o;
        return corpseId == that.corpseId && mobile.equals(that.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobile, corpseId);
    }
}
