package Tasks.Leaves.FarmRun.Draynor;

import Enums.Flower.FlowerPatchesEnum;
import Enums.Flower.FlowerSeeds;
import Enums.PatchStatus;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmingFlowers.FarmFlowerPatch;

public class DoDraynorFlowerPatch extends Leaf {
    @Override
    public boolean isValid() {
        boolean isPlanted = FlowerPatchesEnum.DRAYNOR_FLOWER_PATCH.getPatchStatus() == PatchStatus.PLANTED;
        boolean shouldDoPatch =                 (
                (FlowerPatchesEnum.DRAYNOR_FLOWER_PATCH.getPatchStatus() == PatchStatus.HARVEST &&
                        getSettings().seedsToPlant.getFlowerSeeds() == FlowerSeeds.WHITE_LILY)  ||
                        getSettings().seedsToPlant.getFlowerSeeds() == FlowerSeeds.NONE
        );

        if(isPlanted && getSettings().draynor_flower_tp == 0) {
            getSettings().draynor_flower_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }


        return
                !isPlanted && !shouldDoPatch;
    }

    @Override
    public int onLoop() {

        return FarmFlowerPatch(FlowerPatchesEnum.DRAYNOR_FLOWER_PATCH);

    }



}
