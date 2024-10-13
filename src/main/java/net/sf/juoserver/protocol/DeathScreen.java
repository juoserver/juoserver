package net.sf.juoserver.protocol;

import net.sf.juoserver.api.DeathAction;

import java.nio.ByteBuffer;
import java.util.Objects;

public class DeathScreen extends AbstractMessage {

    protected static final int CODE = 0x2C;
    private DeathAction action;

    public DeathScreen(DeathAction action) {
        super(DeathScreen.CODE, 2);
        this.action = action;
    }

    @Override
    public ByteBuffer encode() {
        var buffer = super.encode();
        buffer.put((byte) action.getCode());
        return buffer;
    }

    public DeathAction getAction() {
        return action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeathScreen that = (DeathScreen) o;
        return action == that.action;
    }

    @Override
    public String toString() {
        return "DeathScreen{" +
                "action=" + action +
                '}';
    }
}
