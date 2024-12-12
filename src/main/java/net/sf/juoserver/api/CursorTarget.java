package net.sf.juoserver.api;

public enum CursorTarget implements Coded {
    SELECT_OBJECT(0),
    SELECT_LOCATION(1);

    private final int code;

    CursorTarget(int code) {
        this.code = code;
    }

    public static CursorTarget byCode(int code) {
        for (CursorTarget target : values()) {
            if (target.getCode() == code) {
                return target;
            }
        }
        throw new IllegalStateException("Invalid CursorTarget code: " + code);
    }
    @Override
    public int getCode() {
        return code;
    }
}
