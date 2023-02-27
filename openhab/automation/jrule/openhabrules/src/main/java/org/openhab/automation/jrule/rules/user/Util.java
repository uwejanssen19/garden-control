package org.openhab.automation.jrule.rules.user;

import org.openhab.automation.jrule.items.JRuleItem;
import org.openhab.automation.jrule.items.JRuleItemRegistry;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;

public class Util {
    public static float getAbsDiff(JRuleItemEvent event) {  
        float oldValue = event.getOldState().getValueAsDouble().floatValue();
        float newValue = event.getState().getValueAsDouble().floatValue();
     
        return getAbsDiffByValue(oldValue, newValue);
    }
    public static JRuleItem getItemByName(String itemName) {
        return JRuleItemRegistry.get(itemName);
    }
    public static float getAbsDiffByValue(float val1, float val2){
        return Math.abs(val1-val2);
    }
    public static boolean willLogDebug(boolean isSetBureauTemp, boolean diffGreaterTreshold){
        boolean ret = false;
        
        // wanted behaviour
        // if isSetBureauTemp then return false
        // else 
        //  if  diffGreaterTreshold then return false
        // else return true

        
        if (isSetBureauTemp) {
            ret = false;
        } else if (diffGreaterTreshold) {
            ret = false;
        } else{
            ret = true;
        }
        // if ((!isSetBureauTemp && diffGreaterTreshold) || isSetBureauTemp)
        // return false;  // will output via logDebug
        // else 
        // return true;
        return ret;
    }

}