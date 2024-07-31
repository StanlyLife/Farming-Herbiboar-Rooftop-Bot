package Enums;

import org.dreambot.api.utilities.Logger;

public enum CompostEnum {
    ULTRA_COMPOST("Ultracompost", "Ultracompost"),
    SUPER_COMPOST("Supercompost", "Supercompost"),
    COMPOST("Compost", "Normal Compost"),
    NONE("", "");

    private String itemName;
    private String farmingStoreItemName;
    CompostEnum(String itemName, String farmingStoreItemName) {
        this.itemName = itemName;
        this.farmingStoreItemName = farmingStoreItemName;
    }


    public String getItemName() {
        return itemName;
    }

    public String getFarmingStoreItemName() {
        return farmingStoreItemName;
    }

    public static CompostEnum getCompostEnum(String name) {
        for (CompostEnum pickaxe : CompostEnum.values()) {
            if (pickaxe.getItemName().equalsIgnoreCase(name)) {
                return pickaxe;
            }
        }
        Logger.log("Invalid CompostEnum: " + name);
        throw new IllegalArgumentException("Invalid CompostEnum name: " + name);
    }

}
