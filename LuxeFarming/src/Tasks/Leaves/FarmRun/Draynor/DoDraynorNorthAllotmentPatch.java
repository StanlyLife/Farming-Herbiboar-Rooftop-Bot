package Tasks.Leaves.FarmRun.Draynor;

import Enums.Allotment.AllotmentPatchEnum;
import Enums.PatchStatus;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmingAllotments.FarmAllotment;

public class DoDraynorNorthAllotmentPatch extends Leaf {
    @Override
    public boolean isValid() {
        PatchStatus patchStatus = AllotmentPatchEnum.DRAYNOR_ALLOTMENT_PATCH_NORTH.getPatchStatus();
        boolean isPlanted = patchStatus == PatchStatus.PLANTED;
        if(isPlanted && getSettings().draynor_allotment_north_tp == 0) {
            getSettings().draynor_allotment_north_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }
        return patchStatus != PatchStatus.PLANTED;
    }

    @Override
    public int onLoop() {
        return FarmAllotment(AllotmentPatchEnum.DRAYNOR_ALLOTMENT_PATCH_NORTH);
    }
}
