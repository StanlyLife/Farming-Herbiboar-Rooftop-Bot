package Tasks.Leaves.FarmRun.FarmingGuild;

import Enums.Herb.HerbPatchesEnum;
import Enums.PatchStatus;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.settings.ScriptSettings;

import static Areas.FarmingAreas.FarmingGuildHerbArea;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.FarmingActions.FarmHerbs.FarmHerbs;

public class DoFarmingGuildHerbPatch extends Leaf {
    @Override
    public boolean isValid() {
        if(Skill.FARMING.getLevel() < 65) return false;
        boolean isPlanted = HerbPatchesEnum.FARMING_GUILD_HERB_PATCH.getPatchStatus() == PatchStatus.PLANTED;
        if(isPlanted && getSettings().farming_guild_herb_tp == 0) {
            getSettings().farming_guild_herb_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
        }

        int minutesToMillisecondsMultiplier = 60000;
        boolean isReady = (getSettings().farming_guild_herb_tp + ((long) getSettings().seedsToPlant.getHerbSeeds().getTimeToGrow() * minutesToMillisecondsMultiplier)) < System.currentTimeMillis();

        return (!isPlanted || isReady);
    }

    @Override
    public int onLoop() {

        if(!FarmingGuildHerbArea.contains(Players.getLocal())) {
            if(Walking.shouldWalk()) Walking.walk(FarmingGuildHerbArea.getRandomTile());
            return Calculations.random(25,150);
        }

        return FarmHerbs(HerbPatchesEnum.FARMING_GUILD_HERB_PATCH);
    }



}
