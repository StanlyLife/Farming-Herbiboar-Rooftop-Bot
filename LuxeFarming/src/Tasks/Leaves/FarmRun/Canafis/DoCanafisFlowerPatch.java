package Tasks.Leaves.FarmRun.Canafis;

import Enums.Flower.FlowerPatchesEnum;
import Enums.Flower.FlowerSeeds;
import Enums.PatchStatus;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmingFlowers.FarmFlowerPatch;

public class DoCanafisFlowerPatch extends Leaf {
    @Override
    public boolean isValid() {
        boolean isPlanted = FlowerPatchesEnum.CANAFIS_FLOWER_PATCH.getPatchStatus() == PatchStatus.PLANTED;
        boolean ShouldDoPatch =                 (
                ((FlowerPatchesEnum.CANAFIS_FLOWER_PATCH.getPatchStatus() == PatchStatus.HARVEST &&
                        getSettings().seedsToPlant.getFlowerSeeds() == FlowerSeeds.WHITE_LILY)) ||
                        getSettings().seedsToPlant.getFlowerSeeds() == FlowerSeeds.NONE
        );

        if(isPlanted && getSettings().canafis_flower_tp == 0) {
            getSettings().canafis_flower_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }


        return
                !isPlanted && !ShouldDoPatch;
    }

    @Override
    public int onLoop() {

        return FarmFlowerPatch(FlowerPatchesEnum.CANAFIS_FLOWER_PATCH);

    }



}
