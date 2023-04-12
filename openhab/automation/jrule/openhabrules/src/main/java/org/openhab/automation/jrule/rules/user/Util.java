package org.openhab.automation.jrule.rules.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.openhab.automation.jrule.generated.items._Set_Bureau_IR_temp;
import org.openhab.automation.jrule.items.JRuleItem;
import org.openhab.automation.jrule.items.JRuleItemRegistry;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;

public class Util {
    public static float getAbsDiff(JRuleItemEvent event) {
        float oldValue = event.getOldState().getValueAsDouble().floatValue();
        float newValue = event.getState().getValueAsDouble().floatValue();

        return getAbsDiffByValue(oldValue, newValue);
    }

    public static float getAbsDiffByValue(float val1, float val2) {
        return Math.abs(val1 - val2);
    }
    public static int getHour() {
        return ZonedDateTime.now().getHour();
    }

    
    /**
     * @param eventItemName
     * @param diffGreaterTreshold
     * 
     *  Behaviour
     *  Output false ( supposed to display log level INFO ) if input is meaningful
     *  This will be the case if Bureau temperature gradient is major
     *  Output true ( supposed to display log level DEBUG) if the temp gradient is minor or the triggering item is Set_Bureau_R_Temp
     * 
     *  if eventName = Set_Bureau_IR_Temp then
     *   return false
     *  else
     *   if diffGreaterTreshold then return false
     *  else
     *   return true
     *
     * 
     * @return
     */
    public static boolean willLogDebug(String eventItemName, boolean diffGreaterTreshold) {
        
        String expectedItemName = _Set_Bureau_IR_temp.ITEM;
        boolean isSetBureauTemp = eventItemName.equals(expectedItemName);
        // not always working:
        // boolean isSetBureauTemp = eventItemName == (expectedItemName);
        if ((!isSetBureauTemp && diffGreaterTreshold) || isSetBureauTemp)
            return false; // will output via logDebug
        else
            return true;
    }

}