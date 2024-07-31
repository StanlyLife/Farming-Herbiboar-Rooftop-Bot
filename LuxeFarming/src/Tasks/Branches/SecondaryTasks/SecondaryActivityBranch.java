package Tasks.Branches.SecondaryTasks;

import Enums.FarmingAreaEnum;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;
import static Settings.secondarySettings.getSecondSettings;

public class SecondaryActivityBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSecondSettings().action != "";
    }
}
