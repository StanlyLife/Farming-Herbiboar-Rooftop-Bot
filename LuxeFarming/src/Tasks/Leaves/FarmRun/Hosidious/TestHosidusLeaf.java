package Tasks.Leaves.FarmRun.Hosidious;

import org.dreambot.api.script.frameworks.treebranch.Leaf;

import static Utils.SelectNextPatch.SelectNextFarmingArea;

public class TestHosidusLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        SelectNextFarmingArea();
        return 5000;
    }








}
