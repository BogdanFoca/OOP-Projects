package simulation;

import utils.JSONOutput;

import java.util.List;

public interface GiftStrategy {
    /**
     * Sorts the list based on strategy
     * @param outputChildren list to sort
     */
    void applyStrategy(List<JSONOutput.OutputChild> outputChildren);
}
