package Enums.Allotment;

import Enums.PatchStatus;
import org.dreambot.api.utilities.Logger;

public enum AllotmentSeeds {
    NONE("", "", "", 0, 0, false),
    POTATO_SEEDS("Potato seed", "Potato", "Potato", 4 * 10, 1, false),
    ONION_SEEDS("Onion seed", "Onion", "Onion", 4*10, 1, true),
    CABBAGE_SEED("Cabbage seed", "Cabbages", "Cabbage", 4*10, 1, false),
    TOMATO_SEED("Tomato seed", "Tomato", "Tomato", 4*10, 1, true),
    SWEETCORN_SEED("Sweetcorn seed", "Sweetcorn", "Sweetcorn", 4*10, 1, true),
    STRAWBERRY_SEED("Strawberry seed", "Strawberry", "Strawberry", 4*10, 1, true),
    WATERMELON_SEED("Watermelon seed", "Watermelons", "Watermelon", 4*10, 1, true),
    SNAPE_GRASS_SEED("Snape grass seed", "Snape grass", "Snape grass", 4*10, 1, true);

    private String seedName;
    private String plantName;
    private String produceName;
    private int timeToGrow; //multiplied by 10
    private int levelToPlant;
    private boolean isNoteable;
    AllotmentSeeds(String seedName, String plantName, String resourceName, int time, int levelToPlant, boolean isNoteable) {
        this.seedName = seedName;
        this.plantName = plantName;
        this.produceName = resourceName;
        this.timeToGrow = time;
        this.levelToPlant = levelToPlant;
        this.isNoteable = isNoteable;
    }

    public static final String[] AllotmentPlantNames = {
            POTATO_SEEDS.getPlantName(),
            ONION_SEEDS.getPlantName(),
            CABBAGE_SEED.getPlantName(),
            TOMATO_SEED.getPlantName(),
            SWEETCORN_SEED.getPlantName(),
            STRAWBERRY_SEED.getPlantName(),
            WATERMELON_SEED.getPlantName(),
            SNAPE_GRASS_SEED.getPlantName(),
    };
    public static final String[] AllotmentProduceNames = {
            POTATO_SEEDS.getProduceName(),
            ONION_SEEDS.getProduceName(),
            CABBAGE_SEED.getProduceName(),
            TOMATO_SEED.getProduceName(),
            SWEETCORN_SEED.getProduceName(),
            STRAWBERRY_SEED.getProduceName(),
            WATERMELON_SEED.getProduceName(),
            SNAPE_GRASS_SEED.getProduceName(),
    };

    public static AllotmentSeeds getAllotmentSeedByProduceName(String name) {
        for (AllotmentSeeds act : AllotmentSeeds.values()) {
            if (act.getProduceName().equalsIgnoreCase(name)) {
                return act;
            }
        }
        Logger.log("Invalid allotment produce: " + name);
        throw new IllegalArgumentException("Invalid allotment produce: " + name);
    }

    public String getSeedName() {
        return seedName;
    }
    public String getPlantName() {
        return this.plantName;
    }

    public String getProduceName() {
        return produceName;
    }


    public int getTimeToGrow() {
        return timeToGrow;
    }

    public int getLevelToPlant() {
        return levelToPlant;
    }

    public boolean isNoteable() {
        return isNoteable;
    }
}
