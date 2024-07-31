package Tasks.Leaves.FarmRun.Canafis;

import Enums.Herb.HerbPatchesEnum;
import Enums.PatchStatus;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmHerbs.FarmHerbs;

public class DoCanafisHerbPatch extends Leaf {
    @Override
    public boolean isValid() {
        boolean isPlanted = HerbPatchesEnum.CANAFIS_HERB_PATCH.getPatchStatus() == PatchStatus.PLANTED;
        if(isPlanted && getSettings().canafis_herb_tp == 0) {
            getSettings().canafis_herb_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }
        return !isPlanted;
    }

    @Override
    public int onLoop() {
        return FarmHerbs(HerbPatchesEnum.CANAFIS_HERB_PATCH);
    }



}
