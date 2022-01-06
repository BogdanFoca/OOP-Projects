package simulation;

import utils.JSONOutput;

import java.util.List;

public interface GiftStrategy {
    void applyStrategy(List<JSONOutput.OutputChild> outputChildren);
}
