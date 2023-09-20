package iterators.tuple3iterator;

import boxedtypes.option.Option;
import datastructures.DataSet;
import iterators.TupleIterator;

import java.util.Iterator;

public sealed interface Tuple_3Iterator<A, B, C> extends Iterator<DataSet.Tuple_3<A, B, C>>, TupleIterator
        permits EqualLengthTuple_3Iterator, UnequalLengthTuple_3Iterator {
    static <A, B, C> Tuple_3Iterator<A, B, C> ofEqualLength(Iterator<A> first, Iterator<B> second, Iterator<C> third) {
        return new EqualLengthTuple_3Iterator<>(first, second, third);
    }

    static <A, B, C> Tuple_3Iterator<Option<A>, Option<B>, Option<C>> ofUnequalLength(Iterator<A> first, Iterator<B> second, Iterator<C> third) {
        return new UnequalLengthTuple_3Iterator<>(first, second, third);
    }
}
