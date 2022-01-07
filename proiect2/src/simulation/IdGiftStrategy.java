package simulation;

import database.Database;
import utils.Comparers;
import utils.JSONOutput;

import java.util.List;

public final class IdGiftStrategy implements GiftStrategy {

    @Override
    public void applyStrategy(final List<JSONOutput.OutputChild> outputChildren) {
        Database.getInstance().getChildren().sort(new Comparers.CompareChildrenById());
        outputChildren.sort(new Comparers.CompareOutputChildrenById());
    }
}
