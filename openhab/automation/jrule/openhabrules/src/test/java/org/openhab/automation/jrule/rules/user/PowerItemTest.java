package org.openhab.automation.jrule.rules.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class PowerItemTest {
    @Test
    public void checkIsInRangeON() {
        assertTrue(PowerItems.getInstance().isInRange(PowerItems.HEATING_STATES.ON));
    }

    @Test
    public void checkIsInRangeOFF() {
        assertTrue(PowerItems.getInstance().isInRange(PowerItems.HEATING_STATES.OFF));
    }

    @Test
    public void checkIsInRangeHeating() {
        assertTrue(PowerItems.getInstance().isInRange(PowerItems.HEATING_STATES.heating));
    }

    @Test
    public void checkIsInRangeStandby() {
        assertTrue(PowerItems.getInstance().isInRange(PowerItems.HEATING_STATES.standby));
    }

    @Test
    public void checkDeviceInMapPumpe() {
        assertTrue(PowerItems.getInstance().isDeviceInMap(PowerItems.HEATING_DEVICE.PumpeFussbodenheizung));
    }

    @Test
    public void checkDeviceInMapBureauIR() {
        assertTrue(PowerItems.getInstance().isDeviceInMap(PowerItems.HEATING_DEVICE.Bureau_IR_work_state));
    }
    @Test
    public void isInRangeForValidValue() {
        PowerItems.HEATING_STATES state = PowerItems.HEATING_STATES.valueOf("standby");
        assertEquals(PowerItems.HEATING_STATES.standby,state);
    }
    @Test
    public void isInRangeForInValidValue() {
        assertThrows(IllegalArgumentException.class,() -> {PowerItems.HEATING_STATES.valueOf("unknown state");});
    }
}