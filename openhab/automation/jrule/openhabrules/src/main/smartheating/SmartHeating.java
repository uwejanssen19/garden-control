package org.openhab.automation.jrule.rules.user.smartheating;

import org.openhab.automation.jrule.items.JRuleNumberItem;
// NOT YET working: import static org.openhab.automation.jrule.generated.items.JRuleItemNames.Alerting;
import org.openhab.automation.jrule.rules.JRuleLogName;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRuleWhenItemChange;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;
import org.openhab.automation.jrule.rules.user.Util;

import java.util.Calendar;
import java.util.Date;

import org.openhab.automation.jrule.generated.actions.JRuleActions;
import org.openhab.automation.jrule.generated.items._Bureau_IR_mode;
import org.openhab.automation.jrule.generated.items._Bureau_IR_on_off;
import org.openhab.automation.jrule.generated.items._Bureau_IR_temp;
import org.openhab.automation.jrule.generated.items._Bureau_IR_work_state;
import org.openhab.automation.jrule.generated.items._GridPower;
import org.openhab.automation.jrule.generated.items._PumpeFussbodenheizung;
import org.openhab.automation.jrule.generated.items._SOC;
import org.openhab.automation.jrule.generated.items._Set_Bureau_IR_temp;
import org.openhab.automation.jrule.generated.items._Solar600_P_AC;
import org.openhab.automation.jrule.generated.items._Solar_power_calculated;

public class SmartHeating extends JRuleBase {

    public static final String MYRULE_NAME = "JSmartHeating";
    boolean heatingPeriod = Util.getHour() > 8 && Util.getHour() < 23;
    // use the set value if not LOW_TEMP
    public static final int HIGH_TEMP = 26;
    public static final int LOW_TEMP = 18;
    public static final int SETTING_OF_BUREAU_TEMP = getItemByName(_Set_Bureau_IR_temp.ITEM);
    public static final int COMFORT_TEMP = (SETTING_OF_BUREAU_TEMP > LOW_TEMP) ? SETTING_OF_BUREAU_TEMP: HIGH_TEMP;
    private static final float HEATING_POWER = 0.8; // kW
    private static final float solarPower =  getItemByName(_Solar_power_calculated.ITEM); //   Solar_power_calculated.state as Number // kW
    private static final float soc = getItemByName(_SOC.ITEM);// SOC.state // battery level
    private static final int MIN_SOC = 75; // percent charge
    private static final boolean enoughBattery = (soc > MIN_SOC);
    //val Number gridpower = (GridPower.state as Number)*1000
    private static final float MIN_GRID_FEED = 1; // kW
    private static final float MAX_SOLAR_POWER = 10; // kW , maximum 8kW peak from the roof and maximum 1kW from the mini PV
    private static final float gridPowerAverage = 0;
    private static final float solarPowerMini = getItemByName(_Solar600_P_AC.ITEM);// (Solar600_P_AC.state as Number)/1000 //  kW
    private static final float gridPower = getItemByName(_GridPower.ITEM);
    private static final float actualTemperature = getItemByName(_Bureau_IR_temp.ITEM);

    /**
     * @param event
     * 
     *              input:
     *              GridPower
     *              Solar_power_calculated
     *              SOC
     *              Bureau_IR_on_off
     *              Set_Bureau_IR_Temp
     *              Bureau_IR_temp
     * 
     *              When enough power : turn ON IR heating and turn down raditor 
     *              else vice versa
     * 
     */
    @JRuleName(MYRULE_NAME)
    @JRuleLogName(MYRULE_NAME)
    @JRuleWhenItemChange(item = _GridPower.ITEM)
    @JRuleWhenItemChange(item = _Solar_power_calculated.ITEM)
    @JRuleWhenItemChange(item = _SOC.ITEM)
    @JRuleWhenItemChange(item = _Bureau_IR_on_off.ITEM)
    @JRuleWhenItemChange(item = _Set_Bureau_IR_temp.ITEM)
    @JRuleWhenItemChange(item = _Bureau_IR_temp.ITEM)
    public synchronized void smartHeating(JRuleItemEvent event) {
        logDebug("SmartHeating ENTRY","Entry due to change of {}, value = {}",getEventName(event), getEventValue(event));
        logDebug("SmartHeating","Using {} as COMFORT_TEMP",COMFORT_TEMP);
    //  plausi check 
    // plausi check gridPower value
    if (gridPower > MAX_SOLAR_POWER) {
      logError("SmartHeating","max grid power exceeded: gridPower [{}] kW > MAX_SOLAR_POWER [{}] kW. DO NOTHING, just leave.",gridPower, MAX_SOLAR_POWER);
      return;
    } else {
      // value in Range, so persist gridPower and its average in influx DB for grafana usage
      JRuleActions.persist(_GridPower);
      gridPowerAverage = GridPower.averageSince(now.minusMinutes(10));
      // store item 
      GridAverageProxy.postUpdate(gridPowerAverage);
      // store in DB
      JRuleActions.persist(_GridAverageProxy);
      logDebug("SmartHeating","valid grid power: {} < {} [kW]. Store in DB ",gridPower, MAX_SOLAR_POWER)
    }





    }
    public SmartHeatingResult handleEvent(JRuleItemEvent event, SmartHeatingInput input) {
        SmartHeatingResult result = new SmartHeatingResult();
        return result;

    }

}