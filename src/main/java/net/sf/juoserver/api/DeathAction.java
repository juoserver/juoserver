package net.sf.juoserver.api;

public enum DeathAction implements Coded {
    SERVER_SENT(0),
    RESURRECT(1),
    GHOST(2);

    private final int code;

    DeathAction(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return 0;
    }
}
