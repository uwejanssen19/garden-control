package org.openhab.automation.jrule.rules.user;

import org.openhab.automation.jrule.generated.actions.JRuleActions;
import org.openhab.automation.jrule.items.JRuleItem;
import org.openhab.automation.jrule.items.JRuleItemRegistry;
import org.openhab.automation.jrule.rules.JRule;
import org.openhab.automation.jrule.rules.event.JRuleItemEvent;

public class JRuleBase extends JRule {
    protected static final String RULE_SUFFIX = "_RULE";

    protected void sendMail(String subject, String msg) {
        JRuleActions.mailSmtpMysmtp.sendMail("uwe@janssen-muenchen.de", subject, msg);
    }

    protected String makeLogText(final String logName, final JRuleItemEvent event) {

        final String itemName = event.getItemName();
        final String logText = logName + " " + itemName + "  changed from " + event.getOldState().getValue() + " to "
                + event.getState().getValue();
        return logText;
    }

    protected String getEventName(JRuleItemEvent event) {
        return event.getItemName();
    }

    protected String getEventValue(JRuleItemEvent event) {
        return event.getState().getValue();
    }
    public static JRuleItem getItemByName(String itemName) {
        return JRuleItemRegistry.get(itemName);
    }

}

class JRuleBaseException extends Exception {
    public JRuleBaseException(String msg) {
        super(JRuleBaseException.class.getName() + " : " + msg);
    }
}
