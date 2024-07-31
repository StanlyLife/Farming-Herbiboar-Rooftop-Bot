package Tasks.Branches;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.script.frameworks.treebranch.Leaf;

public class InventoryHandlerBranch extends Branch {
    @Override
    public boolean isValid() {
        return Inventory.isFull();
    }
}
