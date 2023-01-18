package net.sf.juoserver.api;

public enum AnimationDirection implements Coded {
    FORWARD(0x00),
    BACKWARD(0X01);

    private final int code;

    AnimationDirection(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
