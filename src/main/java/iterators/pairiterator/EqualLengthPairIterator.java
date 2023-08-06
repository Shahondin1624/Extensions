package iterators.pairiterator;

import datastructures.DataSet;
import iterators.IteratorUnequalLengthException;
import utils.Utilities;

import java.util.Iterator;

public class EqualLengthPairIterator<A, B> implements PairIterator<A, B> {
    private final Iterator<A> first;
    private final Iterator<B> second;

    protected EqualLengthPairIterator(Iterator<A> first, Iterator<B> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean hasNext() throws IteratorUnequalLengthException {
        boolean firstNext = first.hasNext();
        boolean secondNext = second.hasNext();
        if (!Utilities.booleanEqual(firstNext, secondNext)) {
            throw new IteratorUnequalLengthException();
        }
        return firstNext;
    }

    @Override
    public DataSet.Pair<A, B> next() {
        A firstNext = first.next();
        B secondNext = second.next();
        return new DataSet.Pair<>(firstNext, secondNext);
    }

    @Override
    public void remove() {
        first.remove();
        second.remove();
    }
}
