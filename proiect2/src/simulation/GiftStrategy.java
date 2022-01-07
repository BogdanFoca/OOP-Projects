package simulation;

import utils.JSONOutput;

import java.util.List;

public interface GiftStrategy {
    /**
     *
     * @param outputChildren
     */
    void applyStrategy(List<JSONOutput.OutputChild> outputChildren);
}
