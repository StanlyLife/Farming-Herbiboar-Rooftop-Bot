package Tasks.Leaves.FarmRun.Draynor;

import Enums.Allotment.AllotmentPatchEnum;
import Utils.SelectNextPatch;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;

import static Enums.Allotment.AllotmentPatchEnum.getClosestAllotmentPatch;
import static Settings.FarmerSettings.getSettings;
import static Settings.FarmerSettings.SAVENAME;
import static Utils.SelectNextPatch.SelectNextFarmingArea;

import Enums.Widgets.FarmingStoreEnum;

public class TestDraynorLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        SelectNextFarmingArea();
        return 5000;
    }








}
