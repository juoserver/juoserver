package net.sf.juoserver.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UOMobileTest {

    private UOMobile  mobile;

    @BeforeEach
    public void setUp() {
        mobile = new UOMobile();
    }

    @Test
    @DisplayName("Should a mobile be killed")
    void shouldMobileBeKilled() {
        mobile.kill();
        assertEquals(0, mobile.getCurrentHitPoints());
        assertEquals(0, mobile.getCurrentMana());
        assertEquals(0, mobile.getCurrentStamina());
        assertTrue(mobile.isDeath());
        assertEquals(UOMobile.DEATH_MODEL_ID, mobile.getModelId());
    }

    @Test
    @DisplayName("Should a killed mobile revive")
    void shouldKilledMobileRevive() {
        mobile.kill();
        assertEquals(UOMobile.DEATH_MODEL_ID, mobile.getModelId());
        mobile.revive();
        assertEquals(UOMobile.ALIVE_MODEL_ID, mobile.getModelId());
    }

}