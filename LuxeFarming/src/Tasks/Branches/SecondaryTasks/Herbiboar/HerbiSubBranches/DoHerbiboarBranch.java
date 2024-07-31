package Tasks.Branches.SecondaryTasks.Herbiboar.HerbiSubBranches;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.herbiboarSettings.getHerbiSettings;

public class DoHerbiboarBranch extends Branch {
    @Override
    public boolean isValid() {
        return Inventory.getEmptySlots() >= getHerbiSettings().minimumEmptySlots;
    }
}
