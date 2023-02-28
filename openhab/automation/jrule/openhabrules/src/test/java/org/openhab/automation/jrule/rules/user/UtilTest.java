package org.openhab.automation.jrule.rules.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilTest {
    @Test
    public void getDiffFor21AND22() {
        assertTrue(Util.getAbsDiffByValue(21, 24) == 3);
    }

    @Test
    public void getDiff1() {
        assertTrue(Util.getAbsDiffByValue(22, 22) == 0);
    }

    @Test
    public void getDiff2() {
        assertTrue(Util.getAbsDiffByValue(-21, -24) == 3);
    }

    @Test
    public void getDiff3() {
        assertTrue(Util.getAbsDiffByValue(-22, -21) == 1);
    }

    @Test
    public void getDiff4() {
        assertTrue(Util.getAbsDiffByValue(-22, 21) > 0);
    }

    @Test
    public void checkIfDebugOutputWhenSetBureauTempIsTheMember() {
        assertFalse("Will always log INFO when member is SetBureauTemp",Util.willLogDebug("Set_Bureau_IR_temp", false));
    }

    @Test
    public void checkIfDebugOutputWhenBureauTempIsTheMemberAndDiffSmall() {
        assertTrue("Will log DEBUG",Util.willLogDebug("Bureau_IR_temp", false));
    }

    @Test
    public void checkIfDebugOutputWhenBureauTempIsTheMemberAndDiffBigEnough() {
        assertFalse("Will log INFO",Util.willLogDebug("Bureau_IR_temp", true));
    }

}
