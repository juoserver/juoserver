package net.sf.juoserver.api;

public enum CursorType implements Coded {

    NEUTRAL(0),
    HARMFUL(1),
    HELPFUL(2),
    CANCEL(3);

    private final int code;

    CursorType(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    public static CursorType byCode(int code) {
        for (CursorType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalStateException("Invalid cursor type code: " + code);
    }
}
