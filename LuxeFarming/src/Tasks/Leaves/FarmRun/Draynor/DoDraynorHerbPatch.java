package Tasks.Leaves.FarmRun.Draynor;

import Enums.Herb.HerbPatchesEnum;
import Enums.PatchStatus;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmHerbs.FarmHerbs;

public class DoDraynorHerbPatch extends Leaf {
    @Override
    public boolean isValid() {
        boolean isPlanted = HerbPatchesEnum.DRAYNOR_HERB_PATCH.getPatchStatus() == PatchStatus.PLANTED;
        if(isPlanted && getSettings().draynor_herb_tp == 0) {
            getSettings().draynor_herb_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }
        return !isPlanted;
    }

    @Override
    public int onLoop() {
        return FarmHerbs(HerbPatchesEnum.DRAYNOR_HERB_PATCH);
    }



}
