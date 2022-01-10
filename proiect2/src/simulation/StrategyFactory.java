package simulation;

import enums.Strategies;

public final class StrategyFactory {

    private StrategyFactory() {

    }

    /**
     * Creates strategy based on argument
     * @param strategy what strategy to create
     * @return
     */
    public static GiftStrategy createGiftStrategy(final Strategies strategy) {
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
