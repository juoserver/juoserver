package net.sf.juoserver.protocol;

import net.sf.juoserver.api.AnimationDirection;
import net.sf.juoserver.api.AnimationRepeat;
import net.sf.juoserver.api.AnimationType;
import net.sf.juoserver.api.Mobile;

import java.nio.ByteBuffer;
import java.util.Objects;

public class CharacterAnimation extends AbstractMessage {

    private static final int CODE = 0x6E;

    private Mobile mobile;
    private AnimationRepeat repeat;
    private AnimationType type;
    private int frameCount;
    private AnimationDirection direction;

    public CharacterAnimation(Mobile mobile, AnimationRepeat repeat, AnimationType type, int frameCount, AnimationDirection direction) {
        super(CODE, 14);
        this.mobile = mobile;
        this.repeat = repeat;
        this.type = type;
        this.frameCount = frameCount;
        this.direction = direction;
    }

    @Override
    public ByteBuffer encode() {
        var buffer = super.encode();
        buffer.putInt(mobile.getSerialId());
        buffer.putShort((short) type.getCode());
        buffer.put((byte) 0x00); // UNKNOWN
        buffer.put((byte) frameCount);
        buffer.putShort((short) repeat.getCode());
        buffer.put((byte) direction.getCode());
        buffer.put((byte) 0);
        buffer.put((byte) 0x00);
        return buffer;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public AnimationRepeat getRepeat() {
        return repeat;
    }

    public AnimationType getType() {
        return type;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public AnimationDirection getDirection() {
        return direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobile, repeat, type, frameCount, direction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterAnimation that = (CharacterAnimation) o;
        return frameCount == that.frameCount && mobile.equals(that.mobile) && repeat == that.repeat && type == that.type && direction == that.direction;
    }

    @Override
    public String toString() {
        return "CharacterAnimation{" +
                "mobile=" + mobile +
                ", type=" + type +
                '}';
    }
}
