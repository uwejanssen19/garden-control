package org.openhab.automation.jrule.rules.user;

import static org.openhab.automation.jrule.generated.items.JRuleItemNames.Bureau_IR_group;
import org.openhab.automation.jrule.rules.JRuleLogName;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRuleWhenItemChange;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;

public class BureauGroupChange extends JRuleBase {
    public static final String MYRULECLASS = "BureauGroupChange";
    public static final String MYLOGNAME = MYRULECLASS + "_RULE";

    @JRuleName(MYRULECLASS)
    @JRuleLogName(MYLOGNAME)
    @JRuleWhenItemChange(item = Bureau_IR_group)
    public synchronized void groupMySwitchGroupChanged(JRuleItemEvent event) {
        final String memberThatChangedStatus = ((JRuleItemEvent) event).getMemberName();
        logInfo(MYLOGNAME + " {} changed to {}", memberThatChangedStatus, event.getState());
    }
}
