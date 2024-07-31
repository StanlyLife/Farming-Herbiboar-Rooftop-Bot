package Tasks.Branches.FarmRun.Draynor;

import Enums.FarmingAreaEnum;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import static Settings.FarmerSettings.getSettings;

public class FarmDraynorBranch extends Branch {

    @Override
    public boolean isValid() {

        return getSettings().currentPatch.getArea().contains(Players.getLocal()) && getSettings().currentPatch == FarmingAreaEnum.DRAYNOR;

    }
}
