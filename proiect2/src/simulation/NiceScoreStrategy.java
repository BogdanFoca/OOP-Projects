package simulation;

import database.Database;
import utils.Comparers;
import utils.JSONOutput;

import java.util.Collections;
import java.util.List;

public final class NiceScoreStrategy implements GiftStrategy {
    @Override
    public void applyStrategy(final List<JSONOutput.OutputChild> outputChildren) {
        Database.getInstance().getChildren().sort(new Comparers.CompareChildrenByNiceScore());
        Collections.reverse(Database.getInstance().getChildren());
        outputChildren.sort(new Comparers.CompareOutputChildrenByNiceScore());
        Collections.reverse(outputChildren);
    }
}
