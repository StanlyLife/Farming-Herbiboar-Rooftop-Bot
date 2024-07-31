package Tasks.Branches.FarmRun.FarmingGuild;

import Enums.FarmingAreaEnum;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;

public class FarmFarmingGuildBranch extends Branch {
    @Override
    public boolean isValid() {

        return getSettings().currentPatch.getArea().contains(Players.getLocal()) && getSettings().currentPatch == FarmingAreaEnum.FARMING_GUILD;

    }
}
