package Enums;

import Enums.Widgets.FarmingStoreEnum;
import org.dreambot.api.utilities.Logger;

public enum PatchStatus {
    CLEAR("Clear"),
    HARVEST("Harvest"),
    CURE("Cure"),
    RAKE("Rake"),
    PLANTED(""),
    UNKNOWN(""),
    PLANT("");

    private String action;
    PatchStatus(String action) {
        this.action = action;
    }

    public static PatchStatus getPatchStusByAction(String name) {
        for (PatchStatus act : PatchStatus.values()) {
            if (act.getAction().equalsIgnoreCase(name)) {
                return act;
            }
        }
        Logger.log("Invalid fishingtype: " + name);
        throw new IllegalArgumentException("Invalid fishingtype name: " + name);
    }

    public String getAction() {
        return action;
    }
}
