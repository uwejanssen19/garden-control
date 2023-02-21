package org.openhab.automation.jrule.rules.user;

import org.openhab.automation.jrule.generated.items._MyTestSwitch;
//import static org.openhab.automation.jrule.generated.items.JRuleItems.MyTestSwitch;
import org.openhab.automation.jrule.rules.JRule;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRuleWhenItemReceivedCommand;


public class MySwitchRule extends JRule {

    @JRuleName("MySwitchRule")
    @JRuleWhenItemReceivedCommand(item = _MyTestSwitch.ITEM/*, to = JRuleSwitchItem.ON */)


    public void myMethod() {
        logError("myMethod: JRuleWhenItemReceivedCommand ||||| -->  Hello JAR World!");
    }
}
