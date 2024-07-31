package Tasks.Leaves.FarmRun.Trollheim;

import Enums.Herb.HerbPatchesEnum;
import Enums.PatchStatus;
import Utils.FarmingActions.FarmTrollheimHerbs;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmHerbs.FarmHerbs;
import static Utils.FarmingActions.FarmTrollheimHerbs.farmTrollheimHerbs;
import static Utils.FarmingActions.FarmTrollheimHerbs.getTrollheimPatchStatus;

public class DoTrollheimHerbPatch extends Leaf {
    @Override
    public boolean isValid() {
        boolean isPlanted = getTrollheimPatchStatus() == PatchStatus.PLANTED;
        if(isPlanted && getSettings().trollheim_herb_tp == 0) {
            getSettings().trollheim_herb_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }
        return !isPlanted;
    }

    @Override
    public int onLoop() {
        return farmTrollheimHerbs(HerbPatchesEnum.TROLLHEIM_HERB_PATCH);
    }



}
