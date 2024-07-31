package Tasks.Branches.SecondaryTasks.VolcanicAsh;

import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.secondarySettings.getSecondSettings;

public class VolcanicAshBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSecondSettings().action == "ash";
    }
}
