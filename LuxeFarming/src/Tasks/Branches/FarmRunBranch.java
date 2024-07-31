package Tasks.Branches;

import Enums.FarmingAreaEnum;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;

public class FarmRunBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSettings().currentPatch != FarmingAreaEnum.NONE;
    }
}
