package net.sf.juoserver.api;

public enum AnimationRepeat implements Coded {
    ONCE(1),
    TWICE(2),
    FOREVER(0);

    private final int code;

    AnimationRepeat(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
