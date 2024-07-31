package Tasks.Leaves.FarmRun.FarmingGuild;

import Enums.Flower.FlowerPatchesEnum;
import Enums.Flower.FlowerSeeds;
import Enums.PatchStatus;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Areas.FarmingAreas.FarmingGuildAllotmentArea;
import static Areas.FarmingAreas.FarmingGuildHerbArea;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmingFlowers.FarmFlowerPatch;

public class DoFarmingGuildFlowerPatch extends Leaf {
    @Override
    public boolean isValid() {
        boolean isPlanted = FlowerPatchesEnum.FARMING_GUILD_FLOWER_PATCH.getPatchStatus() == PatchStatus.PLANTED;
        boolean shouldDoPatch =                 (
                (FlowerPatchesEnum.FARMING_GUILD_FLOWER_PATCH.getPatchStatus() == PatchStatus.HARVEST &&
                        getSettings().seedsToPlant.getFlowerSeeds() == FlowerSeeds.WHITE_LILY)  ||
                        getSettings().seedsToPlant.getFlowerSeeds() == FlowerSeeds.NONE
        );

        if(isPlanted && getSettings().farming_guild_flower_tp == 0) {
            getSettings().farming_guild_flower_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }


        return
                !isPlanted && !shouldDoPatch;
    }

    @Override
    public int onLoop() {

//        if(!FarmingGuildAllotmentArea.contains(Players.getLocal())) {
//            if(Walking.shouldWalk()) Walking.walk(FarmingGuildAllotmentArea.getRandomTile());
//            return Calculations.random(25,150);
//        }

        return FarmFlowerPatch(FlowerPatchesEnum.FARMING_GUILD_FLOWER_PATCH);

    }



}
