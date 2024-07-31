package Tasks.Branches.FarmRun.Trollheim;

import Enums.FarmingAreaEnum;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;

public class FarmTrollheimBranch extends Branch {

    @Override
    public boolean isValid() {
        return getSettings().currentPatch.getArea().contains(Players.getLocal()) && getSettings().currentPatch == FarmingAreaEnum.TROLLHEIM;
    }

}
