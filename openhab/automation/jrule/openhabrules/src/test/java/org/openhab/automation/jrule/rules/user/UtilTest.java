package org.openhab.automation.jrule.rules.user;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class UtilTest {
    /**
     * 
     */
    @Test
    public void getDiffFor21AND22() {
        // var state = new JRuleEventState("21");
        // var oldState = new JRuleEventState("22");

        // var event = new JRuleItemEvent("MockItem", null, state, oldState);

        // assertEquals(1,(int)Util.getAbsDiff(event));
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
        //assertFalse("Will always log INFO when member is SetBureauTemp",BureauChange.willLogDebug(true, true));
        assertFalse("Will always log INFO when member is SetBureauTemp",Util.willLogDebug(true, false));
    }
    @Test
    public void checkIfDebugOutputWhenBureauTempIsTheMemberAndDiffSmall() {
        assertTrue("Will log DEBUG",Util.willLogDebug(false, false));
    }
    @Test
    public void checkIfDebugOutputWhenBureauTempIsTheMemberAndDiffBigEnough() {
        assertFalse("Will log INFO",Util.willLogDebug(false, true));
    }

}
