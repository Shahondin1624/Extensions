package iterators.pairiterator;

import boxedtypes.option.Option;
import datastructures.DataSet;
import utils.Utilities;

import java.util.Iterator;

public class UnequalLengthPairIterator<A, B> implements PairIterator<Option<A>, Option<B>> {
    private final Iterator<A> first;
    private final Iterator<B> second;

    protected UnequalLengthPairIterator(Iterator<A> first, Iterator<B> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean hasNext() {
        return first.hasNext() || second.hasNext();
    }

    @Override
    public DataSet.Pair<Option<A>, Option<B>> next() {
        Option<A> firstOption = Utilities.getAsOption(first::hasNext, first::next);
        Option<B> secondOption = Utilities.getAsOption(second::hasNext, second::next);
        return new DataSet.Pair<>(firstOption, secondOption);
    }
}
