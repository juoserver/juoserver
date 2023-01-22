package net.sf.juoserver.api;

public enum TextType implements Coded {
    NORMAL(0x00),
    SYSTEM(0x01),
    EMOTE(0x02),
    SYSTEM_LOWER_CORNER(0x06),
    CORNER_WITH_NAME(0x07),
    WHISPER(0x08),
    YELL(0x09),
    SPELL(0x0A),
    GUILD_CHAT(0x0D),
    ALLIANCE_CHAT(0x0E),
    COMMAND_PROMPT(0x0F);

    private final int code;

    TextType(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
