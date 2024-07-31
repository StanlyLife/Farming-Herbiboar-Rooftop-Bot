package Tasks.Branches.UtilBranches;

import Settings.FarmerSettings;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.FarmerSettings.getSettings;

public class DeathBranch extends Branch {
    @Override
    public boolean isValid() {
        FarmerSettings.lowestHpPercentage = Math.min(FarmerSettings.lowestHpPercentage, Players.getLocal().getHealthPercent());
        return FarmerSettings.lowestHpPercentage <= 10;
    }
}
