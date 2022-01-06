package simulation;

import utils.Comparers;
import utils.JSONOutput;

import java.util.Comparator;
import java.util.List;

public class NiceScoreStrategy implements GiftStrategy{
    @Override
    public void applyStrategy(List<JSONOutput.OutputChild> outputChildren) {
        outputChildren.sort(new Comparers().new CompareOutputChildrenByNiceScore());
    }
}
