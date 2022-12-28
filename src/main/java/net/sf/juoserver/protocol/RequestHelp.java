package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Decodable;

@Decodable(code=RequestHelp.CODE)
public class RequestHelp extends AbstractMessage {

    protected static final int CODE = 0x9B;

    public RequestHelp(byte[] data) {
        super(CODE, 258);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
