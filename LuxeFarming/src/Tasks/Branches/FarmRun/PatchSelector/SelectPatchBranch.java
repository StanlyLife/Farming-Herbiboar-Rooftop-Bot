package Tasks.Branches.FarmRun.PatchSelector;

import Enums.FarmingAreaEnum;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;
import static Settings.secondarySettings.getSecondSettings;

public class SelectPatchBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSecondSettings().action == "" && getSettings().currentPatch == FarmingAreaEnum.NONE || getSettings().currentPatch == null;
    }
}
