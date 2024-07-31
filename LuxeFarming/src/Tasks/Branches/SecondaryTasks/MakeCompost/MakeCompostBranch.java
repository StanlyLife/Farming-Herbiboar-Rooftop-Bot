package Tasks.Branches.SecondaryTasks.MakeCompost;

import org.dreambot.api.script.frameworks.treebranch.Branch;

import static Settings.secondarySettings.getSecondSettings;

public class MakeCompostBranch extends Branch {
    @Override
    public boolean isValid() {
        return getSecondSettings().action == "make_compost";
    }
}
