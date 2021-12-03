package utils;

import entities.Gift;

import java.util.Comparator;

public final class Comparers {
    public static class CompareGiftsByPrice implements Comparator<Gift> {

        @Override
        public int compare(Gift o1, Gift o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    }
}
