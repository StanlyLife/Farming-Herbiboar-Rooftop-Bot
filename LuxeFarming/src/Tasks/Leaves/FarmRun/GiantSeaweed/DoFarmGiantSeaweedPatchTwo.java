package Tasks.Leaves.FarmRun.GiantSeaweed;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import static Areas.FarmingAreas.*;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmGiantSeaweed.FarmGianSeaweed;

public class DoFarmGiantSeaweedPatchTwo extends Leaf {
    @Override
    public boolean isValid() {
        return FossilIslandUnderWaterArea.contains(Players.getLocal()) && ((getSettings().giant_seaweed_two_tp + 20 * 60 * 1000) <= System.currentTimeMillis());
    }

    @Override
    public int onLoop() {
        return FarmGianSeaweed(fossil_island_seaweed_under_water_patch_two);
    }
}
