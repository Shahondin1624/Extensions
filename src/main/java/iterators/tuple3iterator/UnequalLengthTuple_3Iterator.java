package iterators.tuple3iterator;

import boxedtypes.option.Option;
import datastructures.DataSet;
import utils.Utilities;

import java.util.Iterator;

public class UnequalLengthTuple_3Iterator<A, B, C> implements Tuple_3Iterator<Option<A>, Option<B>, Option<C>> {
    private final Iterator<A> first;
    private final Iterator<B> second;
    private final Iterator<C> third;

    public UnequalLengthTuple_3Iterator(Iterator<A> first, Iterator<B> second, Iterator<C> third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean hasNext() {
        return first.hasNext() || second.hasNext() || third.hasNext();
    }

    @Override
    public DataSet.Tuple_3<Option<A>, Option<B>, Option<C>> next() {
        Option<A> firstOption = Utilities.getAsOption(first::hasNext, first::next);
        Option<B> secondOption = Utilities.getAsOption(second::hasNext, second::next);
        Option<C> thirdOption = Utilities.getAsOption(third::hasNext, third::next);
        return new DataSet.Tuple_3<>(firstOption, secondOption, thirdOption);
    }
}
