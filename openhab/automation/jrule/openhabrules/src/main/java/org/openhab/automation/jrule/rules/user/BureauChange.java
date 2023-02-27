package org.openhab.automation.jrule.rules.user;

import org.openhab.automation.jrule.generated.items._Bureau_IR_temp;
import org.openhab.automation.jrule.generated.items._Set_Bureau_IR_temp;
import org.openhab.automation.jrule.rules.JRuleLogName;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRuleWhenItemChange;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;

public class BureauChange extends JRuleBase {
    public static final String MYRULECLASS = "BureauChange";
    public static final String MYLOGNAME = MYRULECLASS + "_RULE";
    public static final float MAX_DIFF = 2; 

    @JRuleName(MYRULECLASS)
    @JRuleLogName(MYLOGNAME)
    @JRuleWhenItemChange(item = _Bureau_IR_temp.ITEM)
    @JRuleWhenItemChange(item = _Set_Bureau_IR_temp.ITEM)
    public synchronized void bureauTempChanged(JRuleItemEvent event) {
        final String itemName = event.getItemName();
        final String logText = MYLOGNAME + " "+itemName+"  changed from "+event.getOldState().getValue()+" to "+event.getState();

        if (itemName == _Set_Bureau_IR_temp.ITEM && Util.getAbsDiff(event) < MAX_DIFF)         {
            logDebug("DEBUG "+logText);
        } else {
            logInfo("INFO "+logText);
        }
        // boolean ret = false;
        // boolean bt = false;
        // boolean diff_gt_2 = false;

        // if ( bt && !diff_gt_2)
        //     logDebug(logText);
        // else 
        //     logInfo(logText);
         

    }

}
