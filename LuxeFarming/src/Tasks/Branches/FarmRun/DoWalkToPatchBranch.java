package Tasks.Branches.FarmRun;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.utilities.Logger;

import static Settings.FarmerSettings.getSettings;

public class DoWalkToPatchBranch extends Branch {
    @Override
    public boolean isValid() {
        return !getSettings().currentPatch.getArea().contains(Players.getLocal());
    }
}
