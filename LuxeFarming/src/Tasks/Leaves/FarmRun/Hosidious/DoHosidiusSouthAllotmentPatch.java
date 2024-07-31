package Tasks.Leaves.FarmRun.Hosidious;

import Enums.Allotment.AllotmentPatchEnum;
import Enums.PatchStatus;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmingAllotments.FarmAllotment;

public class DoHosidiusSouthAllotmentPatch extends Leaf {
    @Override
    public boolean isValid() {
        PatchStatus patchStatus = AllotmentPatchEnum.HOSIDIOUS_ALLOTMENT_PATCH_SOUTH.getPatchStatus();
        boolean isPlanted = patchStatus == PatchStatus.PLANTED;
        if(isPlanted && getSettings().hosidious_allotment_south_tp == 0) {
            getSettings().hosidious_allotment_south_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }
        return patchStatus != PatchStatus.PLANTED;
    }

    @Override
    public int onLoop() {
        return FarmAllotment(AllotmentPatchEnum.HOSIDIOUS_ALLOTMENT_PATCH_SOUTH);
    }
}
