package utils;

import entities.Gift;

import java.util.Comparator;

public final class Comparers {
    public static final class CompareGiftsByPrice implements Comparator<Gift> {

        @Override
        public int compare(final Gift o1, final Gift o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    }

    public class CompareOutputChildrenById implements Comparator<JSONOutput.OutputChild> {

        @Override
        public int compare(JSONOutput.OutputChild o1, JSONOutput.OutputChild o2) {
            return o1.getId() - o2.getId();
        }
    }

    public class CompareOutputChildrenByNiceScore implements Comparator<JSONOutput.OutputChild> {

        @Override
        public int compare(JSONOutput.OutputChild o1, JSONOutput.OutputChild o2) {
            return o1.getAverageScore().compareTo(o2.getAverageScore());
        }
    }
}
