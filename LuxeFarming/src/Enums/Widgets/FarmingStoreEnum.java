package Enums.Widgets;

import Identifiers.FarmingStoreWidgetId;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import static Identifiers.FarmingStoreWidgetId.*;
import static Settings.FarmerSettings.getSettings;

public enum FarmingStoreEnum {
        RAKE("Farming rake", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_RAKE, CHILD_WIDGET_FARMING_INVENTORY_RAKE),
        SEED_DIBBER("Seed dibber", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_SEED_DIBBER, CHILD_WIDGET_FARMING_INVENTORY_DIBBER),
        SPADE("Spade", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_SPADE, CHILD_WIDGET_FARMING_INVENTORY_SPADE),
        SECATEURS("Secateurs", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_SECATEURS, CHILD_WIDGET_FARMING_INVENTORY_SECATEURS),
        WATERING_CAN("Watering can", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_WATERING_CAN, CHILD_WIDGET_FARMING_INVENTORY_WATERING_CAN),
        GARDENING_TROWEL("Gardening trowel", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_GARDENING_TROWEL, CHILD_WIDGET_FARMING_INVENTORY_TROWEL),
        PLANT_CURE("Plant cure", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_PLANT_CURE, CHILD_WIDGET_FARMING_INVENTORY_PLANT_CURE),
        BOTTOMLESS_BUCKET("Bottomless bucket", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_BOTTOMLESS_BUCKET,CHILD_WIDGET_FARMING_INVENTORY_BOTTOMLESS_BUCKET),
        EMPTY_BUCKET("Empty bucket", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_EMPTY_BUCKETS, CHILD_WIDGET_FARMING_INVENTORY_BUCKETS),
        COMPOST("Normal Compost", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_NORMAL_COMPOST, CHILD_WIDGET_FARMING_INVENTORY_COMPOST),
        SUPERCOMPOST("Supercompost", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_SUPER_COMPOST, CHILD_WIDGET_FARMING_INVENTORY_SUPERCOMPOST),
        ULTRACOMPOST("Ultracompost", FarmingStoreWidgetId.CHILD_WIDGET_FARMING_STORE_ULTRA_COMPOST, CHILD_WIDGET_FARMING_INVENTORY_ULTRACOMPOST);

        private String itemName;
        private int widgetId;
        private int widgetInventoryId;

        FarmingStoreEnum(String itemName, int widgetId, int widgetInventoryId) {
            this.itemName = itemName;
            this.widgetId = widgetId;
            this.widgetInventoryId = widgetInventoryId;
        }


    public static FarmingStoreEnum getFarmingStoreWidget(String name) {
        for (FarmingStoreEnum pickaxe : FarmingStoreEnum.values()) {
            if (pickaxe.getName().equalsIgnoreCase(name)) {
                return pickaxe;
            }
        }
        Logger.log("Invalid fishingtype: " + name);
        throw new IllegalArgumentException("Invalid fishingtype name: " + name);
    }

    public String getName() {
        return itemName;
    }
    public int getWidgetId() {
        return widgetId;
    }

    public WidgetChild getWidget() {
        Widget farmingEquipmentStore = Widgets.getWidget(125);
        if(farmingEquipmentStore == null) {
            Logger.log("Farming equipment store is not open");
            return null;
        }
        return farmingEquipmentStore.getChild(widgetId);
    }
    public WidgetChild getInventoryWidget() {
        Widget farmingEquipmentStore = Widgets.getWidget(126);
        if(farmingEquipmentStore == null) {
            Logger.log("Farming equipment store is not open");
            return null;
        }
        return farmingEquipmentStore.getChild(widgetInventoryId);
    }

    public int getItemsInStore() {
        Widget farmingEquipmentStore = Widgets.getWidget(125);
        if(farmingEquipmentStore == null) {
            Logger.log("Farming equipment store is not open");
            return 0;
        }
        WidgetChild itemQuantity = farmingEquipmentStore.getChild(widgetId).getChild(CHILD_WIDGET_FARMING_STORE_ITEM_AMOUNT);
        Logger.log("Available " + itemName + " : " + itemQuantity.getText().split("/")[0]);
        return Integer.parseInt(itemQuantity.getText().split("/")[0]);
    }
    public boolean hasInStore() {
        Widget farmingEquipmentStore = Widgets.getWidget(125);
        if(farmingEquipmentStore == null) {
            Logger.log("Farming equipment store is not open");
            return false;
        }
        WidgetChild itemQuantity = farmingEquipmentStore.getChild(widgetId).getChild(CHILD_WIDGET_FARMING_STORE_ITEM_AMOUNT);
        Logger.log("Available " + itemName + " : " + itemQuantity.getText().split("/")[0]);
        return Integer.parseInt(itemQuantity.getText().split("/")[0]) > 0;
    }

    public static boolean OpenFarmingStore() {
        NPC leprechaun = NPCs.closest("Tool Leprechaun");
        int searchCount = 0;
        while(NPCs.closest("Tool Leprechaun") == null) {

            if(searchCount > 10) {
                Logger.log("Unable to find tool leprechaun, quitting");
                ScriptManager.getScriptManager().stop();
            }

            Logger.log("Unable to find tool leprechaun, searching");
            Walking.walk(getSettings().currentPatch.getArea().getCenter());
            Sleep.sleepUntil(() -> NPCs.closest("Tool Leprechaun") != null || !Players.getLocal().isMoving(), 5000);
            Sleep.sleep(50,250);
            searchCount++;
        }
        leprechaun.interact("Exchange");
        Sleep.sleepUntil(() -> FarmingStoreEnum.IsToolLeprechaunOpen(), 15000);
        if(FarmingStoreEnum.IsToolLeprechaunOpen()) {
            return true;
        }
        return false;
    }

    public static boolean IsToolLeprechaunOpen() {
        Widget farmingEquipmentStore = Widgets.getWidget(125);
        if(farmingEquipmentStore == null) return false;
        WidgetChild farmingStoreHeader = farmingEquipmentStore.getChild(1);
        if(farmingStoreHeader == null) return false;
        WidgetChild farmingStoreTitle = farmingStoreHeader.getChild(1);
        if(farmingStoreTitle == null || !farmingStoreTitle.getText().toLowerCase().contains("farming equipment")) return false;
        Logger.log("farming store exists");
        return true;
    }
    public boolean WithdrawOne() {

        Widget farmingEquipmentStore = Widgets.getWidget(125);
        if(farmingEquipmentStore == null) {
            Logger.log("Farming equipment store is not open");
            return false;
        }
        int itemsInInventory = Inventory.count(itemName);
        FarmingStoreEnum farmingStoreWidget = getFarmingStoreWidget(itemName);
        farmingStoreWidget.getWidget().interact("Remove-1");
        Sleep.sleepUntil(() -> Inventory.count(itemName) > itemsInInventory, 2500);
        Sleep.sleep(50,250);
        if(Inventory.count(itemName) > itemsInInventory) {
            farmingEquipmentStore.getChild(1).getChild(11).interact();
            Sleep.sleep(50,250);
            return true;
        }
        Logger.log("Unable to withdraw item");
        return false;
    }

    public boolean DespositAll() {

        Widget farmingEquipmentStoreInventory = Widgets.getWidget(126);
        if(farmingEquipmentStoreInventory == null) {
            Logger.log("Farming equipment store is not open");
            return false;
        }
        if(getInventoryWidget().interact("Store-All")) {
            Sleep.sleepUntil(() -> !Inventory.contains(itemName), 2500);
            Sleep.sleep(50,250);
            return true;
        }
        Logger.log("Unable to withdraw item");
        return false;
    }

    public boolean WithdrawMax() {

        Widget farmingEquipmentStore = Widgets.getWidget(125);
        if(farmingEquipmentStore == null) {
            Logger.log("Farming equipment store is not open");
            return false;
        }
        int itemsInInventory = Inventory.count(itemName);
        FarmingStoreEnum farmingStoreWidget = getFarmingStoreWidget(itemName);
        farmingStoreWidget.getWidget().interact("Remove-all");
        Sleep.sleepUntil(() -> Inventory.count(itemName) > itemsInInventory, 2500);
        Sleep.sleep(50,250);
        if(Inventory.count(itemName) > itemsInInventory) {
            farmingEquipmentStore.getChild(1).getChild(11).interact();
            Sleep.sleep(50,250);
            return true;
        }
        Logger.log("Unable to withdraw item");
        return false;
    }
    public boolean FarmingStoreSetQuantityOne() {
        Widget farmingEquipmentStore = Widgets.getWidget(125);
        if(farmingEquipmentStore == null) {
            Logger.log("Farming equipment store is not open");
            return false;
        }

        //set quantity to one
        WidgetChild quantityOne = farmingEquipmentStore.getChild(4);
        quantityOne.interact();
        Sleep.sleep(50,250);
        return true;
    }

}
