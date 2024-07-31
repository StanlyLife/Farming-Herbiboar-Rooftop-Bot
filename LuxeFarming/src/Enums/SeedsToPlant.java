package Enums;

import Enums.Allotment.AllotmentSeeds;
import Enums.Flower.FlowerSeeds;
import Enums.Herb.HerbSeeds;

import static Enums.Allotment.AllotmentSeeds.*;

public enum SeedsToPlant {
//    DEFAULT(SWEETCORN_SEED, HerbSeeds.TARROMIN_SEED, FlowerSeeds.LIMPWURT);
DEFAULT(NONE, HerbSeeds.SNAPDRAGON_SEED, FlowerSeeds.NONE);
//    DEFAULT(CABBAGE_SEED, HerbSeeds.KWUARM_SEED, FlowerSeeds.LIMPWURT);

    private final AllotmentSeeds allotmentSeed;
    private final HerbSeeds herbSeeds;
    private final FlowerSeeds flowerSeeds;

    SeedsToPlant(AllotmentSeeds allotment, HerbSeeds herbSeed, FlowerSeeds flowerSeeds) {

        this.allotmentSeed = allotment;
        this.herbSeeds = herbSeed;
        this.flowerSeeds = flowerSeeds;
    }

    public AllotmentSeeds getAllotmentSeed() {
        return allotmentSeed;
    }

    public HerbSeeds getHerbSeeds() {
        return herbSeeds;
    }

    public FlowerSeeds getFlowerSeeds() {
        return flowerSeeds;
    }
}
