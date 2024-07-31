package Tasks.Leaves.Walking;

import Enums.FarmingAreaEnum;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;

import static Areas.FarmingAreas.CanafisFarmingPatch;
import static Settings.FarmerSettings.getSettings;

public class DoWalkToPatch extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {

        boolean isFeralVampyreInCanafis = getSettings().currentPatch == FarmingAreaEnum.CANAFIS && NPCs.closest("Feral Vampyre") != null && FarmingAreaEnum.CANAFIS.getArea().contains(NPCs.closest("Feral Vampyre"));
        if(getSettings().currentPatch != FarmingAreaEnum.CANAFIS && (CanafisFarmingPatch.contains(Players.getLocal()) || CanafisFarmingPatch.distance(Players.getLocal().getTile()) < 25)) {
            if(Walking.shouldWalk()) {
                Walking.walk(FarmingAreaEnum.FARMING_GUILD.getArea().getCenter());
                Logger.log("Walking to - " + getSettings().currentPatch.name() + " - to avoid Feral Vampyre");
            }
            return 50;
        }

        if(isFeralVampyreInCanafis) {
            Logger.log("Feral Vampyre detected - walking to escape combat");
            World world = Worlds.getRandomWorld(w -> !w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0 && w.isNormal());
            WorldHopper.hopWorld(world);
            return 5000;
        }

        if(Walking.shouldWalk()) {
            Walking.walk(getSettings().currentPatch.getArea().getCenter());
            Logger.log("Walking to - " + getSettings().currentPatch.name());
        }
        return Calculations.random(50, 600);
    }
}
