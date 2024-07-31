package Tasks.Branches.FarmRun.Canafis;

import Enums.FarmingAreaEnum;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;

public class FarmCanafisBranch extends Branch {
    @Override
    public boolean isValid() {

        return getSettings().currentPatch.getArea().contains(Players.getLocal()) && getSettings().currentPatch == FarmingAreaEnum.CANAFIS;
    }
}
