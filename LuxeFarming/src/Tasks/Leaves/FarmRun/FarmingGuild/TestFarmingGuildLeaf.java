package Tasks.Leaves.FarmRun.FarmingGuild;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

import static Enums.FarmingAreaEnum.FARMING_GUILD;
import static Utils.SelectNextPatch.SelectNextFarmingArea;

public class TestFarmingGuildLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {

        if(FARMING_GUILD.getArea().getCenter().distance(Players.getLocal()) > 2) {
            if(Walking.shouldWalk()) {
                Walking.walk(FARMING_GUILD.getArea().getCenter());
            }
            return Calculations.random(25,250);
        }

        SelectNextFarmingArea();
        return 5000;
    }








}
