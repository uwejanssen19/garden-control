package org.openhab.automation.jrule.rules.user;

import org.openhab.automation.jrule.generated.items._Bureau_IR_temp;
import org.openhab.automation.jrule.generated.items._Set_Bureau_IR_temp;
import org.openhab.automation.jrule.generated.items._Bureau_IR_on_off;
import org.openhab.automation.jrule.generated.items._Bureau_IR_mode;
// NOT YET working: import static org.openhab.automation.jrule.generated.items.JRuleItemNames.Alerting;
import org.openhab.automation.jrule.rules.JRuleLogName;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRuleWhenItemChange;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;

public class BureauChange extends JRuleBase {

    public static final String MYRULE_CLASS_BUREAUCHANGE = "BureauChange";
    public static final String MYLOGNAME_BUREAU_CHANGE = MYRULE_CLASS_BUREAUCHANGE + RULE_SUFFIX;
    public static final float MAX_DIFF = 2;

    /**
     * @param JRuleItemEvent event th event which caused the item to change
     */
    @JRuleName(MYRULE_CLASS_BUREAUCHANGE)
    @JRuleLogName(MYLOGNAME_BUREAU_CHANGE)
    @JRuleWhenItemChange(item = _Bureau_IR_temp.ITEM)
    @JRuleWhenItemChange(item = _Set_Bureau_IR_temp.ITEM)
    public synchronized void bureauTempChanged(JRuleItemEvent event) {
        final String logText = makeLogText(MYLOGNAME_BUREAU_CHANGE, event);
        final String itemName = event.getItemName();
        //logDebug(logText);
        logDebug("NOTE that _Set_Bureau_IR_temp.ITEM = {}", _Set_Bureau_IR_temp.ITEM);
        logDebug("Check wether {} == {}: {}", itemName, _Set_Bureau_IR_temp.ITEM, itemName.equals(_Set_Bureau_IR_temp.ITEM));

        boolean willDisplayLogDebug = Util.willLogDebug(itemName, Util.getAbsDiff(event) > MAX_DIFF);
        if (willDisplayLogDebug) {
            logDebug("DEBUG " + logText);
        } else {
            logInfo("INFO " + logText);
        }
    }


    // only the members of group "Alerting" will cause a mail to be sent when changed 
// rule Bureau_IR_group_Alerting
// when Member of Alerting changed
// then
//   	var actions = getActions("mail","mail:smtp:mysmtp")
//   	actions.sendMail("uwe@janssen-muenchen.de","Alert rule: "+triggeringItem.name + " = "+triggeringItem.state, getEmailText.apply())
// end

    /**
     * @param JRuleItemEvent event th event which caused the item to change
     */
    private final static String MYRULE_CLASS_ALERT_CHANGE  = "NotifyChangeAlertGroup";
    private final static String MYLOGNAME_ALERT_CHANGE = MYRULE_CLASS_ALERT_CHANGE + RULE_SUFFIX;
    @JRuleName(MYRULE_CLASS_ALERT_CHANGE)
    @JRuleLogName(MYLOGNAME_ALERT_CHANGE)
    @JRuleWhenItemChange(item = _Bureau_IR_on_off.ITEM)
    @JRuleWhenItemChange(item = _Bureau_IR_mode.ITEM)
    public synchronized void bureauAlertGroupChanged (JRuleItemEvent event) {
        final String logText = makeLogText(MYLOGNAME_ALERT_CHANGE, event);
        logInfo(logText);
        sendMail(MYLOGNAME_ALERT_CHANGE+ " "+logText,logText);
    }
}
