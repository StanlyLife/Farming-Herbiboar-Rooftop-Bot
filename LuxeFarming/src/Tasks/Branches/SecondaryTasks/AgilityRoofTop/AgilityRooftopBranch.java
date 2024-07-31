package Tasks.Branches.SecondaryTasks.AgilityRoofTop;

import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.secondarySettings.getSecondSettings;

public class AgilityRooftopBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSecondSettings().action == "rooftop";
    }
}
