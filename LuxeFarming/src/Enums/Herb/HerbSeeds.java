package Enums.Herb;

public enum HerbSeeds {
    NONE("", "", "",0, 0, 0, true),
    GUAM_SEED("Guam seed", "herbs", "Grimy guam leaf",199, 80, 9, true),
    MARRENTILL_SEED("Marrentill seed", "herbs", "Grimy marrentill",201, 0, 14, true),
    TARROMIN_SEED("Tarromin seed", "herbs", "Grimy tarromin",203, 80, 19, true),
    HARRALANDER_SEED("Harralander seed", "herbs", "Grimy harralander",205 ,80, 26, true),
    RANARR_SEED("Ranarr seed", "herbs", "Grimy ranarr weed",207, 80, 32, true),
    TOADFLAX_SEED("Toadflax seed", "herbs", "Grimy toadflax",3049, 80, 38, true),
    IRIT_SEED("Irit seed", "herbs", "Grimy irit leaf",209, 80, 48, true),
    AVANTOE_SEED("Avantoe seed", "herbs", "Grimy avantoe",211, 80, 50, true),
    KWUARM_SEED("Kwuarm seed", "herbs", "Grimy kwuarm",213, 80, 56, true),
    SNAPDRAGON_SEED("Snapdragon seed", "herbs", "Grimy snapdragon",3051, 80, 62, true),
    CADANTINE_SEED("Cadantine seed", "herbs", "Grimy cadantine",215, 80, 67, true),
    LANTADYME_SEED("Lantadyme seed", "herbs", "Grimy lantadyme",2458, 80, 73, true),
    DWARF_WEED_SEED("Dwarf weed seed", "herbs", "Grimy dwarf weed",217, 80, 79, true),
    TORSTOL_SEED("Torstol seed", "herbs", "Grimy torstol",219, 80, 85, true);

    private String seedName;
    private String plantName;
    private String produceName;
    private int produceID;
    private int timeToGrow; //multiplied by 10
    private int levelToPlant;
    private boolean isNoteable;

    HerbSeeds(String seedName, String plantName, String produceName,int produceId, int timeToGrow, int levelToPlant, boolean isNoteable) {
        this.seedName = seedName;
        this.plantName = plantName;
        this.produceName = produceName;
        this.timeToGrow = timeToGrow;
        this.levelToPlant = levelToPlant;
        this.isNoteable = isNoteable;
        this.produceID = produceId;
    }


    public String getSeedName() {
        return seedName;
    }

    public String getPlantName() {
        return plantName;
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

    public int getProduceID() {
        return produceID;
    }
}
