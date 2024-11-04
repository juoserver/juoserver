package net.sf.juoserver.model.npc;

import net.sf.juoserver.api.Mobile;

import java.util.Objects;

public class ComposideKey {
    private final Mobile npc;
    private final Mobile mobile;

    public ComposideKey(Mobile npc, Mobile mobile) {
        this.npc = npc;
        this.mobile = mobile;
    }

    public Mobile getNpc() {
        return npc;
    }

    public Mobile getMobile() {
        return mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComposideKey composideKey = (ComposideKey) o;
        return Objects.equals(npc, composideKey.npc) && Objects.equals(mobile, composideKey.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(npc, mobile);
    }
}
