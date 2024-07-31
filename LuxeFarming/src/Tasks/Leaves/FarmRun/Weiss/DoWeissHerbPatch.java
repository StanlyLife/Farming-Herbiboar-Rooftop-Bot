package Tasks.Leaves.FarmRun.Weiss;

import Enums.Herb.HerbPatchesEnum;
import Enums.PatchStatus;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmTrollheimHerbs.farmTrollheimHerbs;
import static Utils.FarmingActions.FarmTrollheimHerbs.getTrollheimPatchStatus;
import static Utils.FarmingActions.FarmWeissHerbs.farmWeissHerbs;
import static Utils.FarmingActions.FarmWeissHerbs.getWeissPatchStatus;

public class DoWeissHerbPatch extends Leaf {
    @Override
    public boolean isValid() {
        boolean isPlanted = getWeissPatchStatus() == PatchStatus.PLANTED;
        if(isPlanted && getSettings().weiss_herb_tp == 0) {
            getSettings().weiss_herb_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }
        return !isPlanted;
    }

    @Override
    public int onLoop() {
        return farmWeissHerbs(HerbPatchesEnum.WEISS_HERB_PATCH);
    }



}
