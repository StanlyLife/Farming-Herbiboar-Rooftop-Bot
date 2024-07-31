package Tasks.Branches.SecondaryTasks.Herbiboar;

import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.secondarySettings.getSecondSettings;

public class HerbiboarMainBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSecondSettings().action == "herbi";
    }
}
