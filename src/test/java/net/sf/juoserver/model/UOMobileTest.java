package net.sf.juoserver.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UOMobileTest {

    private UOMobile  mobile;

    @BeforeEach
    public void setUp() {
        mobile = new UOMobile();
    }

    @Test
    void shouldKillAndReviveMobile() {
        mobile.kill();
        assertEquals(UOMobile.DEATH_MODEL_ID, mobile.getModelId());
        mobile.revive();
        assertEquals(UOMobile.ALIVE_MODEL_ID, mobile.getModelId());
    }

}