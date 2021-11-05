package Entities;

public class Pair<A, B> extends Object {
    private A first;
    private B second;

    public Pair(A first, B second){
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj){
        Pair<A, B> pair = (Pair<A, B>) obj;
        return first.equals(pair.first) && second.equals(pair.second);
    }
}
