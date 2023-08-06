package iterators.pairiterator;

import boxedtypes.option.Option;
import datastructures.DataSet;

import java.util.Iterator;

public interface PairIterator<A, B> extends Iterator<DataSet.Pair<A, B>> {
    static <A, B> PairIterator<A, B> ofEqualLength(Iterator<A> first, Iterator<B> second) {
        return new EqualLengthPairIterator<>(first, second);
    }

    static <A, B> PairIterator<Option<A>, Option<B>> ofUnequalLength(Iterator<A> first, Iterator<B> second) {
        return new UnequalLengthPairIterator<>(first, second);
    }
}
