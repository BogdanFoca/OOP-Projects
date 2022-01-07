package utils;

import entities.Child;
import entities.Gift;

import java.util.Comparator;

public final class Comparers {
    public static final class CompareGiftsByPrice implements Comparator<Gift> {

        @Override
        public int compare(final Gift o1, final Gift o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    }

    public static final class CompareOutputChildrenById implements
            Comparator<JSONOutput.OutputChild> {

        @Override
        public int compare(final JSONOutput.OutputChild o1,
                           final JSONOutput.OutputChild o2) {
            return o1.getId() - o2.getId();
        }
    }

    public static final class CompareChildrenById implements
            Comparator<Child> {

        @Override
        public int compare(final Child o1,
                           final Child o2) {
            return o1.getId() - o2.getId();
        }
    }

    public static final class CompareOutputChildrenByNiceScore implements
            Comparator<JSONOutput.OutputChild> {

        @Override
        public int compare(final JSONOutput.OutputChild o1,
                           final JSONOutput.OutputChild o2) {
            return o1.getAverageScore().compareTo(o2.getAverageScore());
        }
    }

    public static final class CompareChildrenByNiceScore implements
            Comparator<Child> {

        @Override
        public int compare(final Child o1,
                           final Child o2) {
            return o1.getAverageScore().compareTo(o2.getAverageScore());
        }
    }
}
