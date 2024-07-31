package Tasks.Branches.FarmRun.GiantSeaweed;

import Enums.FarmingAreaEnum;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;

public class FarmGiantSeaweed extends Branch {
    @Override
    public boolean isValid() {
        return getSettings().currentPatch == FarmingAreaEnum.FOSSIL_ISLAND_SEAWEED;
    }
}
