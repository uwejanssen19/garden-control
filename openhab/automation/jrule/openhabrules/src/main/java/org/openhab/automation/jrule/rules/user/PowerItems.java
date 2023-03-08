package org.openhab.automation.jrule.rules.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PowerItems {
    enum HEATING_STATES {
        ON("ON"),
        OFF("OFF"),
        heating("heating"),
        standby("standby");
        private String name;

        HEATING_STATES(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    enum HEATING_DEVICE {
        PumpeFussbodenheizung("PumpeFussbodenheizung"),
        Bureau_IR_work_state("Bureau_IR_work_state");
        private String name;
        HEATING_DEVICE (String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    private PowerItems() {
    }

    List<HEATING_STATES> STATE_RANGE = Arrays.asList(HEATING_STATES.ON, HEATING_STATES.OFF, HEATING_STATES.heating,
            HEATING_STATES.standby);

    public boolean isInRange(HEATING_STATES keyValue) {
        return STATE_RANGE.contains(keyValue);
    }

    public boolean isDeviceInMap(HEATING_DEVICE itemName) {
        return DEVICE_LIST.keySet().contains(itemName);
    }

    Map<HEATING_STATES, Integer> stateValueFloor = new HashMap<>() {
        {
            put(HEATING_STATES.ON, Integer.valueOf(60));
            put(HEATING_STATES.OFF, Integer.valueOf(0));
        }
    };
    Map<HEATING_STATES, Integer> stateValueBureau = new HashMap<>() {
        {
            put(HEATING_STATES.heating, Integer.valueOf(800));
            put(HEATING_STATES.standby, Integer.valueOf(0));
        }
    };
    Map<HEATING_DEVICE, Map<HEATING_STATES, Integer>> DEVICE_LIST = new HashMap<>() {
        {
            // note that the keys must match exactly the item names in items/heating.items
            put(HEATING_DEVICE.PumpeFussbodenheizung, stateValueFloor);
            put(HEATING_DEVICE.Bureau_IR_work_state, stateValueBureau);
        }
    };

    public Map<HEATING_DEVICE, Map<HEATING_STATES, Integer>> getDevicelist() {
        return DEVICE_LIST;
    }

    private static PowerItems instance = null;

    // singleton
    /**
     * @return new instance of Poweritems
     */
    public static PowerItems getInstance() {
        if (instance == null) {
            instance = new PowerItems();
        }
        return instance;
    }
}
