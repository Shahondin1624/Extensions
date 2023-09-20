package iterators.tuple3iterator;

import datastructures.DataSet;
import iterators.EqualLengthTupleIterator;
import iterators.IteratorUnequalLengthException;
import utils.Utilities;

import java.util.Iterator;

public final class EqualLengthTuple_3Iterator<A, B, C> implements Tuple_3Iterator<A, B, C>, EqualLengthTupleIterator {
    private final Iterator<A> first;
    private final Iterator<B> second;
    private final Iterator<C> third;

    EqualLengthTuple_3Iterator(Iterator<A> first, Iterator<B> second, Iterator<C> third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean hasNext() throws IteratorUnequalLengthException {
        boolean firstNext = first.hasNext();
        boolean secondNext = second.hasNext();
        boolean thirdNext = third.hasNext();
        if (!Utilities.booleanEqual(firstNext, secondNext, thirdNext)) {
            throw new IteratorUnequalLengthException();
        }
        return firstNext;
    }

    @Override
    public DataSet.Tuple_3<A, B, C> next() {
        A firstNext = first.next();
        B secondNext = second.next();
        C thirdNext = third.next();
        return new DataSet.Tuple_3<>(firstNext, secondNext, thirdNext);
    }

    @Override
    public void remove() {
        first.remove();
        second.remove();
        third.remove();
    }
}
