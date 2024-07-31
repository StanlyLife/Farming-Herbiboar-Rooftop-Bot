package Tasks.Leaves.FarmRun.FarmingGuild;

import Enums.Allotment.AllotmentPatchEnum;
import Enums.PatchStatus;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Areas.FarmingAreas.FarmingGuildAllotmentArea;
import static Enums.Allotment.AllotmentPatchEnum.FARMING_GUILD_ALLOTMENT_PATCH_NORTH;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmingAllotments.FarmAllotment;

public class DoFarmingGuildNorthAllotmentPatch extends Leaf {
    @Override
    public boolean isValid() {
        PatchStatus patchStatus = FARMING_GUILD_ALLOTMENT_PATCH_NORTH.getPatchStatus();
        boolean isPlanted = patchStatus == PatchStatus.PLANTED;
        if(isPlanted && getSettings().farming_guild_allotment_north_tp == 0) {
            getSettings().farming_guild_allotment_north_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }

        return !isPlanted;
    }

    @Override
    public int onLoop() {

        if(FARMING_GUILD_ALLOTMENT_PATCH_NORTH.getPatchTile().distance(Players.getLocal()) >= 10){
            if(Walking.shouldWalk()) Walking.walk(FARMING_GUILD_ALLOTMENT_PATCH_NORTH.getPatchTile());
            return Calculations.random(25,150);
        }

        return FarmAllotment(FARMING_GUILD_ALLOTMENT_PATCH_NORTH);
    }
}
