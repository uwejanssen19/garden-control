package org.openhab.automation.jrule.rules.user;

import java.util.Map;

import org.openhab.automation.jrule.generated.items._Bureau_IR_mode;
import org.openhab.automation.jrule.generated.items._Bureau_IR_on_off;
import org.openhab.automation.jrule.generated.items._Bureau_IR_temp;
import org.openhab.automation.jrule.generated.items._Bureau_IR_work_state;
import org.openhab.automation.jrule.generated.items._PumpeFussbodenheizung;
import org.openhab.automation.jrule.generated.items._Set_Bureau_IR_temp;

import org.openhab.automation.jrule.items.JRuleNumberItem;
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
        // logDebug(logText);
        logDebug("NOTE that _Set_Bureau_IR_temp.ITEM = {}", _Set_Bureau_IR_temp.ITEM);
        logDebug("Check wether {} == {}: {}", itemName, _Set_Bureau_IR_temp.ITEM,
                itemName.equals(_Set_Bureau_IR_temp.ITEM));

        boolean willDisplayLogDebug = Util.willLogDebug(itemName, Util.getAbsDiff(event) > MAX_DIFF);
        if (willDisplayLogDebug) {
            logDebug("DEBUG " + logText);
        } else {
            logInfo("INFO " + logText);
        }
    }
    // DSL rule
    // drawback: getEmailText() method must be defined as a COPY (!) in ALL .rule
    // files where it is invoked
    //
    //
    // only the members of group "Alerting" will cause a mail to be sent when
    // changed
    // rule Bureau_IR_group_Alerting
    // when Member of Alerting changed
    // then
    // var actions = getActions("mail","mail:smtp:mysmtp")
    // actions.sendMail("uwe@janssen-muenchen.de","Alert rule: "+triggeringItem.name
    // + " = "+triggeringItem.state, getEmailText.apply())
    // end

    /**
     * Here comes the java rule
     * 
     * @param JRuleItemEvent event th event which caused the item to change
     */
    private final static String MYRULE_CLASS_ALERT_CHANGE = "NotifyChangeAlertGroup";
    private final static String MYLOGNAME_ALERT_CHANGE = MYRULE_CLASS_ALERT_CHANGE + RULE_SUFFIX;

    @JRuleName(MYRULE_CLASS_ALERT_CHANGE)
    @JRuleLogName(MYLOGNAME_ALERT_CHANGE)
    @JRuleWhenItemChange(item = _Bureau_IR_on_off.ITEM)
    @JRuleWhenItemChange(item = _Bureau_IR_mode.ITEM)
    public synchronized void bureauAlertGroupChanged(JRuleItemEvent event) {
        final String logText = makeLogText(MYLOGNAME_ALERT_CHANGE, event);
        logDebug(logText);
        // sendMail(MYLOGNAME_ALERT_CHANGE + " " + logText, logText);
    }

    /**
     * 
     * @param JRuleItemEvent event thE event which caused the item to change
     * @description
     */
    private final static String MYRULE_CLASS_POWER_ITEM_CHANGE = "PowerItemChange";
    private final static String MYLOGNAME_POWER_CHANGE = MYRULE_CLASS_POWER_ITEM_CHANGE + "_RULE";

    @JRuleName(MYRULE_CLASS_POWER_ITEM_CHANGE)
    @JRuleLogName(MYLOGNAME_POWER_CHANGE)
    @JRuleWhenItemChange(item = _PumpeFussbodenheizung.ITEM)
    @JRuleWhenItemChange(item = _Bureau_IR_work_state.ITEM)
    public synchronized void powerItemChanged(JRuleItemEvent event) throws JRuleBaseException {
        JRuleNumberItem powerItem;
        int powerValue;
        try {
            powerItem = makePowerItemFromEvent(event);
            powerValue = findValue4Event(event);
            logInfo("setting {} to new value  {}", powerItem.getName(), powerValue);
            powerItem.sendCommand(powerValue);
        } catch (Exception jbe) {

            throw new JRuleBaseException(jbe.getMessage());
        }
    }

    private JRuleNumberItem makePowerItemFromEvent(JRuleItemEvent event) throws JRuleBaseException {
        if (event == null) {
            throw new JRuleBaseException("event = null");
        }
        // get name of changing item
        String itemName = event.getItemName();
        if (itemName == null) {
            throw new JRuleBaseException("itemName = null");
        }
        // create the associated _Power item
        String powerItemName = itemName + "_Power";

        // now find the object in the item registry
        JRuleNumberItem powerItem = (JRuleNumberItem) Util.getItemByName(powerItemName);
        if (powerItem == null) {
            throw new JRuleBaseException("powerItem = null");
        }
        return powerItem;

    }

    private int findValue4Event(JRuleItemEvent event) throws JRuleBaseException {
        // get all the devices with their possible states
        Map<PowerItems.HEATING_DEVICE, Map<PowerItems.HEATING_STATES, Integer>> deviceList = PowerItems.getInstance().getDevicelist();
        // now map the value
        String stateValue = event.getState().getValue(); // ON / OFF / heating / standby
        int powerValue;
        String itemName = event.getItemName();
        if (itemName != null) {
            try {
                // find power value for state value
                // now find the appropiate value in the devicelist
                // first find the map for triggering item, will be the HEATING_DEVICE
                PowerItems.HEATING_DEVICE dev = PowerItems.HEATING_DEVICE.valueOf(itemName);
                PowerItems.HEATING_STATES stateKey = PowerItems.HEATING_STATES.valueOf(stateValue);
                Map<PowerItems.HEATING_STATES, Integer> deviceMap = PowerItems.getInstance().isDeviceInMap(dev)
                        ? deviceList.get(dev)
                        : null;
                powerValue = PowerItems.getInstance().isInRange(PowerItems.HEATING_STATES.valueOf(stateValue)) ? deviceMap.get(stateKey).intValue() : 0;
                logDebug("deviceMap = {} ", deviceMap);
            } catch (Exception e) {
                throw new JRuleBaseException(e.getMessage());
            }
        } else {
            throw new JRuleBaseException("itemName = null");
        }
        return powerValue;
    }
}
