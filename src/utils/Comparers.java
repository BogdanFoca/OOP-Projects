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
}
