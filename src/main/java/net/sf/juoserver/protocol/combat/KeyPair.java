package net.sf.juoserver.protocol.combat;

import net.sf.juoserver.api.Mobile;

import java.util.Objects;

record KeyPair(Mobile attacker, Mobile attacked) {
    static KeyPair of(Mobile attacker, Mobile attacked) {
        return new KeyPair(attacker, attacked);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyPair keyPair = (KeyPair) o;
        return Objects.equals(attacker, keyPair.attacker) && Objects.equals(attacked, keyPair.attacked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attacker, attacked);
    }
}
