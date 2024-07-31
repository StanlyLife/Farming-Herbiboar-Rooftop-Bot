package Tasks.Branches.FarmRun.Weiss;

import Enums.FarmingAreaEnum;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;

public class FarmWeissPatchBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSettings().currentPatch == FarmingAreaEnum.WEISS;
    }
}
