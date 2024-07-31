package Enums.Flower;

public enum FlowerSeeds {

    NONE("", "", "", 0,0, false),
    MARIGOLD("Marigold seed", "Marigold", "Marigolds", 4*5,2, false),
    ROSEMARY("Rosemary seed", "Rosemary", "Rosemary", 4*5,11, false),
    NASTURTIUM("Nasturtium seed", "Nasturtium", "Nasturtium", 4*5,24, false),
    WOAD("Woad seed", "Woad", "Woad leaf", 4*5,2, false),
    LIMPWURT("Limpwurt seed", "Limpwurt plant", "Limpwurt root", 4*5,26, true),
    WHITE_LILY("White lily seed", "White lily", "White lily", 4*5,58, false);


    private String seedName;
    private String plantName;
    private String produceName;
    private int timeToGrow; //multiplied by 10
    private int levelToPlant;
    private boolean isNoteable;

    FlowerSeeds(String seedName, String plantName, String produceName, int timeToGrow, int levelToPlant, boolean isNoteable) {
        this.seedName = seedName;
        this.plantName = plantName;
        this.produceName = produceName;
        this.timeToGrow = timeToGrow;
        this.levelToPlant = levelToPlant;
        this.isNoteable = isNoteable;
    }
    public static String[] FlowerPlantNames = {
            "Marigold",
            "Rosemary",
            "Nasturtium",
            "Woad",
            "Limpwurt plant",
            "White lily",
    };
    public static String[] FlowerProduceNames = {
            "Marigolds",
            "Rosemary",
            "Nasturtium",
            "Woad leaf",
            "Limpwurt root",
            "White lily",
    };


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

}
