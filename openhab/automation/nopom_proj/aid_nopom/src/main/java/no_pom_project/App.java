
package org.openhab.automation.jrule.rules.user;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRule;
import org.openhab.automation.jrule.generated.items._MyTestSwitch;
//import org.openhab.automation.jrule.items.JRuleSwitchItem;
import org.openhab.automation.jrule.rules.JRuleWhenItemReceivedCommand;

/**
 * Hello world!
 *
 */
public class App extends JRule
{


    public static final String MY_NAME = "TheName";
    public static final String MSG1 = "show method done";
    public static final String MSG2 = "execOffToOnRule done";
    // public static void main( String[] args )
    // {
    //     //System.out.println( "Hello World!" );
    //     App myApp = new App();
    //     myApp.show(MY_NAME + ":  "+ MSG);
    // }
    @JRuleName(MY_NAME)
	@JRuleWhenItemReceivedCommand(item = _MyTestSwitch.ITEM/*, to = JRuleSwitchItem.ON */)        
                
    public void show() {
//        System.out.println(msg);
        logInfo(MSG1);
    }
    // public void execOffToOnRule() {
    //     logInfo(MSG2);
    // }

}
