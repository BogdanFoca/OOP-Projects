package simulation;

import enums.Strategies;

public class StrategyFactory {
    public static GiftStrategy createGiftStrategy(Strategies strategy) {
        switch (strategy) {
            case ID:
                return new IdGiftStrategy();
            case NICE_SCORE:
                return new NiceScoreStrategy();
            case NICE_SCORE_CITY:
                return new NiceScoreCityStrategy();
            default:
                return null;
        }
    }
}
